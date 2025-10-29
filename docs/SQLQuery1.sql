-- Script: Creación de esquema para Sistema de Torneos de eSports (SQL Server)
-- Ejecutar en la base de datos deseada (USE <tu_bd>;)

SET NOCOUNT ON;
BEGIN TRANSACTION;

--------------------------------------------------------------------------------
-- 1) Tablas de autenticación / roles
--------------------------------------------------------------------------------

-- Tabla Usuario
CREATE TABLE dbo.Usuario (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre_usuario NVARCHAR(100) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    contraseña NVARCHAR(255) NOT NULL, -- almacenar hash
    estado NVARCHAR(20) NOT NULL DEFAULT('activo'), -- 'activo','pendiente','suspendido'
    fecha_creacion DATE NOT NULL DEFAULT SYSUTCDATETIME()
);
-- Restricción de estados permitidos
ALTER TABLE dbo.Usuario
ADD CONSTRAINT chk_usuario_estado CHECK (estado IN ('activo','pendiente','suspendido'));

-- Tabla Rol
CREATE TABLE dbo.Rol (
    id_rol INT IDENTITY(1,1) PRIMARY KEY,
    nombre_rol NVARCHAR(50) NOT NULL UNIQUE, -- 'jugador','organizador','admin', etc.
    descripcion NVARCHAR(400) NULL
);

-- Tabla UsuarioRol (M:N)
CREATE TABLE dbo.UsuarioRol (
    id_usuario INT NOT NULL,
    id_rol INT NOT NULL,
    fecha_asignacion DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    PRIMARY KEY (id_usuario, id_rol),
    CONSTRAINT fk_usuariorol_usuario FOREIGN KEY (id_usuario) REFERENCES dbo.Usuario(id_usuario) ON DELETE CASCADE,
    CONSTRAINT fk_usuariorol_rol FOREIGN KEY (id_rol) REFERENCES dbo.Rol(id_rol) ON DELETE CASCADE
);

--------------------------------------------------------------------------------
-- 2) Catálogo de juegos y configuración
--------------------------------------------------------------------------------

CREATE TABLE dbo.Juego (
    id_juego INT IDENTITY(1,1) PRIMARY KEY,
    nombre_juego NVARCHAR(150) NOT NULL UNIQUE,
    descripcion NVARCHAR(MAX) NULL,
    estado NVARCHAR(30) NOT NULL DEFAULT('disponible'), -- 'disponible','proximamente','en_desarrollo'
    tipo_modalidad NVARCHAR(30) NOT NULL DEFAULT('individual'), -- 'individual','equipos'
    plataforma NVARCHAR(100) NULL, -- opcional: 'PC','Mobile', 'Multiplataforma'
    imagen NVARCHAR(255) NULL,
    fecha_agregado DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
);
ALTER TABLE dbo.Juego
ADD CONSTRAINT chk_juego_estado CHECK (estado IN ('disponible','proximamente','en_desarrollo'));
ALTER TABLE dbo.Juego
ADD CONSTRAINT chk_juego_tipo_modalidad CHECK (tipo_modalidad IN ('individual','equipos'));

-- Configuraciones dinámicas por juego (clave/valor)
CREATE TABLE dbo.JuegoConfiguracion (
    id_config INT IDENTITY(1,1) PRIMARY KEY,
    id_juego INT NOT NULL,
    clave NVARCHAR(100) NOT NULL,
    valor NVARCHAR(200) NOT NULL,
    CONSTRAINT fk_juegoconf_juego FOREIGN KEY (id_juego) REFERENCES dbo.Juego(id_juego) ON DELETE CASCADE
);
CREATE INDEX ix_juegoconf_juego_clave ON dbo.JuegoConfiguracion(id_juego, clave);

--------------------------------------------------------------------------------
-- 3) Torneos y configuración por torneo
--------------------------------------------------------------------------------

CREATE TABLE dbo.Torneo (
    id_torneo INT IDENTITY(1,1) PRIMARY KEY,
    id_juego INT NOT NULL,
    id_organizador INT NOT NULL, -- referencia a Usuario
    nombre_torneo NVARCHAR(200) NOT NULL,
    descripcion NVARCHAR(MAX) NULL,
    modalidad NVARCHAR(40) NOT NULL DEFAULT('eliminacion'), -- 'eliminacion','round_robin','mixto'
    fecha_inicio DATE NULL,
    fecha_fin DATE NULL,
    estado NVARCHAR(30) NOT NULL DEFAULT('inscripciones'), -- 'inscripciones','en_curso','finalizado','cancelado'
    max_equipos INT NULL,
    limite_inscripciones INT NULL,
    premio NVARCHAR(255) NULL,
    fecha_creacion DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_torneo_juego FOREIGN KEY (id_juego) REFERENCES dbo.Juego(id_juego),
    CONSTRAINT fk_torneo_organizador FOREIGN KEY (id_organizador) REFERENCES dbo.Usuario(id_usuario)
);
ALTER TABLE dbo.Torneo
ADD CONSTRAINT chk_torneo_modalidad CHECK (modalidad IN ('eliminacion','round_robin','mixto'));
ALTER TABLE dbo.Torneo
ADD CONSTRAINT chk_torneo_estado CHECK (estado IN ('inscripciones','en_curso','finalizado','cancelado'));
CREATE INDEX ix_torneo_juego ON dbo.Torneo(id_juego);
CREATE INDEX ix_torneo_organizador ON dbo.Torneo(id_organizador);

-- Configuración por torneo (clave/valor)
CREATE TABLE dbo.TorneoConfiguracion (
    id_config INT IDENTITY(1,1) PRIMARY KEY,
    id_torneo INT NOT NULL,
    clave NVARCHAR(100) NOT NULL,
    valor NVARCHAR(200) NOT NULL,
    CONSTRAINT fk_tornconf_torneo FOREIGN KEY (id_torneo) REFERENCES dbo.Torneo(id_torneo) ON DELETE CASCADE
);
CREATE INDEX ix_tornconf_torneo_clave ON dbo.TorneoConfiguracion(id_torneo, clave);

--------------------------------------------------------------------------------
-- 4) Jugadores, equipos y relación
--------------------------------------------------------------------------------

-- Jugador (perfil específico vinculado a Usuario)
CREATE TABLE dbo.Jugador (
    id_jugador INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE, -- 1:1 con Usuario (opcional: no todos los usuarios son jugadores)
    nickname NVARCHAR(100) NOT NULL,
    fecha_registro DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_jugador_usuario FOREIGN KEY (id_usuario) REFERENCES dbo.Usuario(id_usuario) ON DELETE CASCADE
);
CREATE INDEX ix_jugador_nickname ON dbo.Jugador(nickname);

-- Equipo
CREATE TABLE dbo.Equipo (
    id_equipo INT IDENTITY(1,1) PRIMARY KEY,
    nombre_equipo NVARCHAR(150) NOT NULL,
    fecha_creacion DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    id_torneo INT NULL, -- si el equipo pertenece a un torneo (opcional)
    id_capitan INT NULL, -- FK a Jugador (opcional)
    CONSTRAINT fk_equipo_torneo FOREIGN KEY (id_torneo) REFERENCES dbo.Torneo(id_torneo) ON DELETE SET NULL,
    CONSTRAINT fk_equipo_capitan FOREIGN KEY (id_capitan) REFERENCES dbo.Jugador(id_jugador) ON DELETE SET NULL
);
CREATE INDEX ix_equipo_torneo ON dbo.Equipo(id_torneo);

-- Tabla puente EquipoJugador (N:M)
CREATE TABLE dbo.EquipoJugador (
    id_equipo INT NOT NULL,
    id_jugador INT NOT NULL,
    fecha_union DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    rol_en_equipo NVARCHAR(100) NULL, -- 'titular','suplente', etc.
    PRIMARY KEY (id_equipo, id_jugador),
    CONSTRAINT fk_eqjug_equipo FOREIGN KEY (id_equipo) REFERENCES dbo.Equipo(id_equipo) ON DELETE CASCADE,
    CONSTRAINT fk_eqjug_jugador FOREIGN KEY (id_jugador) REFERENCES dbo.Jugador(id_jugador) ON DELETE CASCADE
);
CREATE INDEX ix_eqjug_jugador ON dbo.EquipoJugador(id_jugador);

--------------------------------------------------------------------------------
-- 5) Enfrentamientos y Resultado
--------------------------------------------------------------------------------

CREATE TABLE dbo.Enfrentamiento (
    id_enfrentamiento INT IDENTITY(1,1) PRIMARY KEY,
    id_torneo INT NOT NULL,
    id_equipo1 INT NOT NULL,
    id_equipo2 INT NOT NULL,
    ronda INT NULL,
    fecha_partida DATETIME2 NULL,
    lugar NVARCHAR(255) NULL,
    estado NVARCHAR(30) NOT NULL DEFAULT('pendiente'), -- 'pendiente','jugado','cancelado'
    CONSTRAINT fk_enf_torneo FOREIGN KEY (id_torneo) REFERENCES dbo.Torneo(id_torneo),
    CONSTRAINT fk_enf_equipo1 FOREIGN KEY (id_equipo1) REFERENCES dbo.Equipo(id_equipo),
    CONSTRAINT fk_enf_equipo2 FOREIGN KEY (id_equipo2) REFERENCES dbo.Equipo(id_equipo),
    CONSTRAINT chk_enf_distintos_equipos CHECK (id_equipo1 IS NOT NULL AND id_equipo2 IS NOT NULL AND id_equipo1 <> id_equipo2)
);
ALTER TABLE dbo.Enfrentamiento
ADD CONSTRAINT chk_enfrentamiento_estado CHECK (estado IN ('pendiente','jugado','cancelado'));
CREATE INDEX ix_enfrentamiento_torneo ON dbo.Enfrentamiento(id_torneo);
CREATE INDEX ix_enfrentamiento_fecha ON dbo.Enfrentamiento(fecha_partida);

-- Resultado (1:1 con Enfrentamiento)
CREATE TABLE dbo.Resultado (
    id_resultado INT IDENTITY(1,1) PRIMARY KEY,
    id_enfrentamiento INT NOT NULL UNIQUE,
    id_ganador INT NULL, -- equipo ganador
    marcador NVARCHAR(50) NULL, -- formato flexible "2-1"
    duracion_segundos INT NULL,
    observaciones NVARCHAR(MAX) NULL,
    fecha_registro DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_resultado_enfrentamiento FOREIGN KEY (id_enfrentamiento) REFERENCES dbo.Enfrentamiento(id_enfrentamiento) ON DELETE CASCADE,
    CONSTRAINT fk_resultado_ganador FOREIGN KEY (id_ganador) REFERENCES dbo.Equipo(id_equipo) ON DELETE SET NULL
);
CREATE INDEX ix_resultado_enfrentamiento ON dbo.Resultado(id_enfrentamiento);

--------------------------------------------------------------------------------
-- 6) Clasificación y estadísticas
--------------------------------------------------------------------------------

-- Clasificación por torneo (estado puntual)
CREATE TABLE dbo.Clasificacion (
    id_clasificacion INT IDENTITY(1,1) PRIMARY KEY,
    id_torneo INT NOT NULL,
    id_equipo INT NOT NULL,
    puntos INT NOT NULL DEFAULT 0,
    ganadas INT NOT NULL DEFAULT 0,
    perdidas INT NOT NULL DEFAULT 0,
    empates INT NOT NULL DEFAULT 0,
    posicion INT NULL,
    CONSTRAINT fk_clasificacion_torneo FOREIGN KEY (id_torneo) REFERENCES dbo.Torneo(id_torneo) ON DELETE CASCADE,
    CONSTRAINT fk_clasificacion_equipo FOREIGN KEY (id_equipo) REFERENCES dbo.Equipo(id_equipo) ON DELETE CASCADE,
    CONSTRAINT uq_clasificacion_torneo_equipo UNIQUE (id_torneo, id_equipo)
);
CREATE INDEX ix_clasificacion_torneo ON dbo.Clasificacion(id_torneo);

-- Estadísticas acumuladas por jugador (precalculated)
CREATE TABLE dbo.JugadorEstadisticas (
    id_jugador INT PRIMARY KEY,
    torneos_jugados INT NOT NULL DEFAULT 0,
    torneos_ganados INT NOT NULL DEFAULT 0,
    partidas_jugadas INT NOT NULL DEFAULT 0,
    partidas_ganadas INT NOT NULL DEFAULT 0,
    puntos_acumulados INT NOT NULL DEFAULT 0,
    fecha_actualizacion DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    CONSTRAINT fk_jugestad_jugador FOREIGN KEY (id_jugador) REFERENCES dbo.Jugador(id_jugador) ON DELETE CASCADE
);

--------------------------------------------------------------------------------
-- 7) Solicitudes (roles, juegos, otros)
--------------------------------------------------------------------------------

CREATE TABLE dbo.Solicitud (
    id_solicitud INT IDENTITY(1,1) PRIMARY KEY,
    id_usuario INT NOT NULL,
    tipo NVARCHAR(50) NOT NULL, -- 'rol','juego','otro'
    referencia_id INT NULL, -- opcional: id relevante (ej id_juego si la solicitud es para agregar juego)
    descripcion NVARCHAR(MAX) NULL,
    estado NVARCHAR(30) NOT NULL DEFAULT('pendiente'), -- 'pendiente','aprobada','rechazada'
    respuesta_admin NVARCHAR(MAX) NULL,
    fecha_solicitud DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    fecha_respuesta DATETIME2 NULL,
    CONSTRAINT fk_solicitud_usuario FOREIGN KEY (id_usuario) REFERENCES dbo.Usuario(id_usuario) ON DELETE CASCADE
);
ALTER TABLE dbo.Solicitud
ADD CONSTRAINT chk_solicitud_estado CHECK (estado IN ('pendiente','aprobada','rechazada'));

--------------------------------------------------------------------------------
-- 8) Índices adicionales recomendados (para rendimiento)
--------------------------------------------------------------------------------

-- Índices ya creados en puntos claves; agregamos algunos más
CREATE INDEX ix_usuario_fecha_creacion ON dbo.Usuario(fecha_creacion);
CREATE INDEX ix_torneo_estado ON dbo.Torneo(estado);
CREATE INDEX ix_enfrentamiento_ronda ON dbo.Enfrentamiento(ronda);

--------------------------------------------------------------------------------
-- 9) Datos iniciales sugeridos
--------------------------------------------------------------------------------

-- Insertar roles base
INSERT INTO dbo.Rol (nombre_rol, descripcion) VALUES
('jugador', 'Rol base: puede participar en torneos'),
('organizador', 'Puede crear y gestionar torneos'),
('admin', 'Administrador del sistema: aprueba solicitudes y gestiona la plataforma');

--------------------------------------------------------------------------------
-- 10) (Opcional) Vista ejemplo para mostrar configuración combinada de torneo
--------------------------------------------------------------------------------
GO  -- 🔹 Cierra todo lo anterior antes de crear la vista

CREATE VIEW dbo.vw_TorneoConfiguracionCombinada AS
SELECT 
    t.id_torneo, 
    t.nombre_torneo, 
    t.id_juego, 
    j.nombre_juego,
    tc.clave AS clave_torneo, 
    tc.valor AS valor_torneo,
    jc.clave AS clave_juego, 
    jc.valor AS valor_juego
FROM dbo.Torneo t
LEFT JOIN dbo.Juego j ON j.id_juego = t.id_juego
LEFT JOIN dbo.TorneoConfiguracion tc ON tc.id_torneo = t.id_torneo
LEFT JOIN dbo.JuegoConfiguracion jc ON jc.id_juego = j.id_juego;
GO  -- 🔹 Cierra el lote del CREATE VIEW

--------------------------------------------------------------------------------
COMMIT TRANSACTION;
PRINT 'Schema creado correctamente.';
GO

IF OBJECT_ID('dbo.vw_JugadoresDetalle', 'V') IS NOT NULL
    DROP VIEW dbo.vw_JugadoresDetalle;
GO

CREATE VIEW dbo.vw_JugadoresDetalle AS
SELECT 
    j.id_jugador,
    j.nickname,
    u.nombre_usuario,
    u.email,
    r.nombre_rol AS rol,
    e.nombre_equipo,
    t.nombre_torneo
FROM dbo.Jugador j
INNER JOIN dbo.Usuario u ON u.id_usuario = j.id_usuario
LEFT JOIN dbo.UsuarioRol ur ON ur.id_usuario = u.id_usuario
LEFT JOIN dbo.Rol r ON r.id_rol = ur.id_rol
LEFT JOIN dbo.EquipoJugador ej ON ej.id_jugador = j.id_jugador
LEFT JOIN dbo.Equipo e ON e.id_equipo = ej.id_equipo
LEFT JOIN dbo.Torneo t ON t.id_torneo = e.id_torneo;
GO

CREATE PROCEDURE sp_AgregarJugador
    @nombre_usuario NVARCHAR(100),
    @email NVARCHAR(255),
    @password_hash NVARCHAR(255),
    @nickname NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRANSACTION;

    BEGIN TRY
        -- 1. Crear usuario
        INSERT INTO dbo.Usuario (nombre_usuario, email, contraseña)
        VALUES (@nombre_usuario, @email, @password_hash);

        DECLARE @id_usuario INT = SCOPE_IDENTITY();

        -- 2. Crear jugador vinculado al usuario
        INSERT INTO dbo.Jugador (id_usuario, nickname)
        VALUES (@id_usuario, @nickname);

        -- 3. Asignar rol 'jugador' automáticamente
        DECLARE @id_rol INT = (SELECT id_rol FROM dbo.Rol WHERE nombre_rol = 'jugador');
        IF @id_rol IS NOT NULL
            INSERT INTO dbo.UsuarioRol (id_usuario, id_rol) VALUES (@id_usuario, @id_rol);

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO
--Ejemplo
EXEC sp_AgregarJugador 'Carlos', 'carlos@email.com', 'hash123', 'Carlitox';
--

GO

CREATE OR ALTER PROCEDURE sp_ActualizarJugador
    @id_usuario INT,
    @nombre_usuario NVARCHAR(100) = NULL,
    @email NVARCHAR(255) = NULL,
    @nickname NVARCHAR(100) = NULL,
    @estado NVARCHAR(20) = NULL,
    @id_rol INT = NULL,
    @password_hash NVARCHAR(255) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRANSACTION;

    BEGIN TRY
        -- Actualizar datos del usuario
        UPDATE dbo.Usuario
        SET 
            nombre_usuario = COALESCE(@nombre_usuario, nombre_usuario),
            email = COALESCE(@email, email),
            contraseña = COALESCE(@password_hash, contraseña),
            estado = COALESCE(@estado, estado)
        WHERE id_usuario = @id_usuario;

        -- Actualizar nickname en Jugador
        UPDATE dbo.Jugador
        SET nickname = COALESCE(@nickname, nickname)
        WHERE id_usuario = @id_usuario;

        -- Actualizar rol del usuario (si existe)
        IF @id_rol IS NOT NULL
        BEGIN
            IF EXISTS (SELECT 1 FROM dbo.UsuarioRol WHERE id_usuario = @id_usuario)
                UPDATE dbo.UsuarioRol SET id_rol = @id_rol WHERE id_usuario = @id_usuario;
            ELSE
                INSERT INTO dbo.UsuarioRol (id_usuario, id_rol) VALUES (@id_usuario, @id_rol);
        END;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        THROW;
    END CATCH
END;
GO

--Eliminar Jugaor

CREATE OR ALTER PROCEDURE dbo.sp_EliminarJugador
    @id_usuario INT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRANSACTION;

    BEGIN TRY
        -- 1) Validar existencia del usuario
        IF NOT EXISTS (SELECT 1 FROM dbo.Usuario WHERE id_usuario = @id_usuario)
            THROW 51010, 'Usuario no encontrado.', 1;

        -- 2) Verificar si tiene rol 'admin'
        IF EXISTS (
            SELECT 1
            FROM dbo.UsuarioRol ur
            JOIN dbo.Rol r ON ur.id_rol = r.id_rol
            WHERE ur.id_usuario = @id_usuario
              AND r.nombre_rol = 'admin'
        )
        BEGIN
            THROW 51011, 'No se puede eliminar un usuario con rol de administrador.', 1;
        END

        -- 3) Eliminar relaciones dependientes de forma explícita y segura
        -- Primero: asociaciones a roles
        DELETE FROM dbo.UsuarioRol WHERE id_usuario = @id_usuario;

        -- Segundo: registros en EquipoJugador relacionados al jugador (si existen)
        DELETE ej
        FROM dbo.EquipoJugador ej
        INNER JOIN dbo.Jugador j ON ej.id_jugador = j.id_jugador
        WHERE j.id_usuario = @id_usuario;

        -- Tercero: eliminar fila en Jugador
        DELETE FROM dbo.Jugador WHERE id_usuario = @id_usuario;

        -- Finalmente: eliminar el usuario
        DELETE FROM dbo.Usuario WHERE id_usuario = @id_usuario;

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        -- Re-lanzar el error original para que el cliente lo reciba tal cual
        THROW;
    END CATCH
END;
GO
