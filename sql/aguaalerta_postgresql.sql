-- ============================================================
-- AguaAlerta - Script para PostgreSQL (Neon)
-- Convertido del script original de SQL Server.
-- Correrlo UNA sola vez en el SQL Editor de Neon.
--
-- Cambios para que funcione la autenticacion:
--   * Se quitaron: create database, use, sys.tables, sys.databases.
--   * Todos los ID son autogenerados (GENERATED ALWAYS AS IDENTITY)
--     y empiezan en 10, como en el script original.
--   * Los INSERT ya no mandan el ID: se generan solos (10, 11, 12...).
--   * Usuarios: correo UNIQUE, campos obligatorios NOT NULL,
--     estado DEFAULT 'activo' y fecha_registro DEFAULT hoy.
--   * Roles: nombre_rol UNIQUE, estado DEFAULT 'activo' y los roles
--     en minusculas (admin, tecnico, ciudadano), igual que permisos.js.
--   * Fechas con guion: '2026-01-10'.
-- ============================================================

CREATE TABLE Roles (
                       IDRol INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                       Nombre_Rol VARCHAR(30) NOT NULL UNIQUE,     -- admin, tecnico, ciudadano
                       Descripcion VARCHAR(300),
                       Estado VARCHAR(20) NOT NULL DEFAULT 'activo'
);

CREATE TABLE Usuarios (
                          IDUsuario INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          Nombre_Usuario VARCHAR(50) NOT NULL,
                          Correo VARCHAR(100) NOT NULL UNIQUE,
                          Contrasena_Hash VARCHAR(255) NOT NULL,
                          Telefono VARCHAR(20),
                          Fecha_Registro DATE NOT NULL DEFAULT CURRENT_DATE,
                          Estado VARCHAR(20) NOT NULL DEFAULT 'activo',
                          IDRol INT NOT NULL,
                          FOREIGN KEY (IDRol) REFERENCES Roles(IDRol)
);

CREATE TABLE Ciudadanos (
                            IDCiudadano INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                            IDUsuario INT,
                            Nombre VARCHAR(100),
                            Apellido_Paterno VARCHAR(50),
                            Apellido_Materno VARCHAR(50),
                            FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario)
);

CREATE TABLE Colonias (
                          IDColonia INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          Nombre_Colonia VARCHAR(100),
                          Municipio VARCHAR(100),
                          Codigo_Postal VARCHAR(10),
                          Descripcion VARCHAR(300),
                          Latitud DECIMAL(9,6),
                          Longitud DECIMAL(9,6),
                          Estado VARCHAR(20)
);

CREATE TABLE Viviendas (
                           IDVivienda INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                           IDColonia INT,
                           Calle VARCHAR(100),
                           Numero_Exterior VARCHAR(10),
                           Numero_Interior VARCHAR(10),
                           Referencia VARCHAR(200),
                           Numero_Habitantes INT,
                           Tipo_Vivienda VARCHAR(50),      -- Casa, Departamento, Vecindad
                           FOREIGN KEY (IDColonia) REFERENCES Colonias(IDColonia)
);

CREATE TABLE Tecnicos (
                          IDTecnico INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          Nombre VARCHAR(100),
                          Apellido_Paterno VARCHAR(50),
                          Apellido_Materno VARCHAR(50),
                          Telefono VARCHAR(20),
                          Correo VARCHAR(100),
                          Especialidad VARCHAR(50),
                          Fecha_Contratacion DATE,
                          Disponibilidad VARCHAR(30)      -- Disponible, Ocupado
);

CREATE TABLE Fuentes_Abastecimiento (
                                        IDFuente INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                        Nombre_Fuente VARCHAR(100),
                                        Tipo_Fuente VARCHAR(50),        -- Pozo, Manantial, Sistema municipal
                                        Ubicacion VARCHAR(100),
                                        Capacidad_Suministro_Litros DECIMAL(12,2),
                                        Descripcion VARCHAR(300),
                                        Estado VARCHAR(20)
);

CREATE TABLE Depositos (
                           IDDeposito INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                           IDColonia INT,
                           Nombre_Deposito VARCHAR(100),
                           Capacidad_Litros DECIMAL(12,2),
                           Nivel_Actual_Litros DECIMAL(12,2),
                           Tipo_Deposito VARCHAR(50),      -- Tanque elevado, Cisterna
                           Ubicacion VARCHAR(100),
                           Fecha_Instalacion DATE,
                           Estado VARCHAR(20),
                           FOREIGN KEY (IDColonia) REFERENCES Colonias(IDColonia)
);

CREATE TABLE Sensores (
                          IDSensor INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          IDDeposito INT,
                          Tipo_Sensor VARCHAR(50),
                          Marca VARCHAR(50),
                          Modelo VARCHAR(50),
                          Fecha_Instalacion DATE,
                          Ultima_Conexion DATE,
                          Estado VARCHAR(20),             -- Activo, Desconectado
                          FOREIGN KEY (IDDeposito) REFERENCES Depositos(IDDeposito)
);

CREATE TABLE Lecturas (
                          IDLectura INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          IDSensor INT,
                          Fecha DATE,
                          Hora TIME,
                          Nivel_Agua_Litros DECIMAL(12,2),
                          Porcentaje_Llenado DECIMAL(5,2),
                          Temperatura DECIMAL(5,2),
                          Observaciones VARCHAR(300),
                          FOREIGN KEY (IDSensor) REFERENCES Sensores(IDSensor)
);

CREATE TABLE Redes_Hidraulicas (
                                   IDRed INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                   Nombre_Red VARCHAR(100),
                                   Tipo_Red VARCHAR(50),           -- Primaria, Secundaria
                                   Longitud_Metros DECIMAL(10,2),
                                   Fecha_Instalacion DATE,
                                   Estado VARCHAR(20),
                                   Descripcion VARCHAR(300)
);

CREATE TABLE Tuberias (
                          IDTuberia INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          IDRed INT,
                          Codigo_Tuberia VARCHAR(20),
                          Material VARCHAR(50),           -- PVC, Cobre, Polietileno
                          Diametro DECIMAL(6,2),
                          Longitud_Metros DECIMAL(10,2),
                          Fecha_Instalacion DATE,
                          Estado VARCHAR(20),
                          Ubicacion VARCHAR(100),
                          FOREIGN KEY (IDRed) REFERENCES Redes_Hidraulicas(IDRed)
);

CREATE TABLE Categorias_Incidencia (
                                       IDCategoria INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                       Nombre_Categoria VARCHAR(50),   -- Fuga de agua, Tuberia danada, Falta de suministro, Deposito vacio, Baja presion, Problemas de infraestructura
                                       Descripcion VARCHAR(300),
                                       Nivel_Gravedad VARCHAR(20),     -- Baja, Media, Alta
                                       Estado VARCHAR(20)
);

CREATE TABLE Reportes (
                          IDReporte INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                          IDUsuario INT,
                          IDCategoria INT,
                          IDTuberia INT,                  -- puede quedar vacio
                          Titulo VARCHAR(100),
                          Descripcion VARCHAR(300),
                          Fecha_Reporte DATE,
                          Hora_Reporte TIME,
                          Ubicacion VARCHAR(100),
                          Fotografia VARCHAR(300),
                          Nivel_Prioridad VARCHAR(20),    -- Baja, Media, Alta
                          Estado VARCHAR(20),             -- Pendiente, En proceso, Resuelto
                          FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario),
                          FOREIGN KEY (IDCategoria) REFERENCES Categorias_Incidencia(IDCategoria),
                          FOREIGN KEY (IDTuberia) REFERENCES Tuberias(IDTuberia)
);

CREATE TABLE Reporte_Vivienda (
                                  IDReporteVivienda INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                  IDReporte INT,
                                  IDVivienda INT,
                                  FOREIGN KEY (IDReporte) REFERENCES Reportes(IDReporte),
                                  FOREIGN KEY (IDVivienda) REFERENCES Viviendas(IDVivienda)
);

CREATE TABLE Asignaciones (
                              IDAsignacion INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                              IDReporte INT,
                              IDTecnico INT,
                              Fecha_Asignacion DATE,
                              Fecha_Inicio DATE,
                              Fecha_Termino DATE,
                              Observaciones VARCHAR(300),
                              Estado VARCHAR(20),
                              Resultado_Atencion VARCHAR(200),
                              FOREIGN KEY (IDReporte) REFERENCES Reportes(IDReporte),
                              FOREIGN KEY (IDTecnico) REFERENCES Tecnicos(IDTecnico)
);

CREATE TABLE Abastecimientos (
                                 IDAbastecimiento INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                 IDDeposito INT,
                                 IDFuente INT,
                                 Responsable VARCHAR(100),       -- responsable de la operacion
                                 Fecha DATE,
                                 Hora TIME,
                                 Litros_Recibidos DECIMAL(12,2),
                                 FOREIGN KEY (IDDeposito) REFERENCES Depositos(IDDeposito),
                                 FOREIGN KEY (IDFuente) REFERENCES Fuentes_Abastecimiento(IDFuente)
);

CREATE TABLE Mantenimientos (
                                IDMantenimiento INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                IDTecnico INT,
                                Tipo_Elemento VARCHAR(20),      -- Deposito, Sensor, Red, Tuberia
                                ID_Elemento INT,                -- ID del deposito, sensor, red o tuberia intervenido
                                Tipo_Mantenimiento VARCHAR(50), -- Preventivo, Correctivo
                                Fecha DATE,
                                Costo DECIMAL(10,2),
                                Observaciones VARCHAR(300),
                                FOREIGN KEY (IDTecnico) REFERENCES Tecnicos(IDTecnico)
);

CREATE TABLE Notificaciones (
                                IDNotificacion INT GENERATED ALWAYS AS IDENTITY (START WITH 10) PRIMARY KEY,
                                IDUsuario INT,
                                Tipo_Origen VARCHAR(20),        -- Deposito, Sensor, Reporte
                                ID_Origen INT,                  -- ID del deposito, sensor o reporte que genero la alerta
                                Tipo_Alerta VARCHAR(50),        -- Nivel bajo, Sensor desconectado, Fuga detectada, Reporte pendiente
                                Mensaje VARCHAR(300),
                                Fecha DATE,
                                Hora TIME,
                                Estado VARCHAR(20),             -- Leida, No leida
                                FOREIGN KEY (IDUsuario) REFERENCES Usuarios(IDUsuario)
);

-- ============================================================
-- DATOS DE EJEMPLO (los ID se generan solos: 10, 11, 12...)
-- ============================================================

-- Roles: 10 = admin, 11 = tecnico, 12 = ciudadano
INSERT INTO Roles (Nombre_Rol, Descripcion, Estado)
VALUES ('admin', 'Asigna tecnicos, consulta estadisticas y revisa alertas', 'activo'),
       ('tecnico', 'Atiende reportes y registra reparaciones y mantenimientos', 'activo'),
       ('ciudadano', 'Registra reportes y consulta el abastecimiento de su colonia', 'activo');

-- Usuarios: 10 a 14
-- OJO: estas contrasenas NO son hashes BCrypt, asi que estos usuarios
-- no pueden hacer login. Para entrar, registrar usuarios nuevos con
-- POST /api/auth/registro.
INSERT INTO Usuarios (Nombre_Usuario, Correo, Contrasena_Hash, Telefono, Fecha_Registro, Estado, IDRol)
VALUES ('admin01', 'admin@aguaalerta.com', 'a1b2c3d4e5', '2221110001', '2026-01-10', 'activo', 10),
       ('tecnico01', 'luis.tec@aguaalerta.com', 'f6g7h8i9j0', '2221110002', '2026-01-15', 'activo', 11),
       ('tecnico02', 'ana.tec@aguaalerta.com', 'k1l2m3n4o5', '2221110003', '2026-02-01', 'activo', 11),
       ('mariap', 'maria.perez@gmail.com', 'p6q7r8s9t0', '2221110004', '2026-03-05', 'activo', 12),
       ('jorgeh', 'jorge.hdz@gmail.com', 'u1v2w3x4y5', '2221110005', '2026-03-12', 'activo', 12);

INSERT INTO Ciudadanos (IDUsuario, Nombre, Apellido_Paterno, Apellido_Materno)
VALUES (13, 'Maria', 'Perez', 'Lopez'),
       (14, 'Jorge', 'Hernandez', 'Ruiz');

INSERT INTO Colonias (Nombre_Colonia, Municipio, Codigo_Postal, Descripcion, Latitud, Longitud, Estado)
VALUES ('Centro', 'San Pedro Cholula', '72760', 'Zona centro con alta demanda', 19.062800, -98.306400, 'Activo'),
       ('La Paz', 'Puebla', '72160', 'Zona residencial', 19.051000, -98.230000, 'Activo'),
       ('San Manuel', 'Puebla', '72570', 'Zona con fugas frecuentes', 19.004000, -98.200000, 'Activo');

INSERT INTO Viviendas (IDColonia, Calle, Numero_Exterior, Numero_Interior, Referencia, Numero_Habitantes, Tipo_Vivienda)
VALUES (10, '5 de Mayo', '120', 'A', 'Frente al parque', 4, 'Casa'),
       (12, 'Av. San Claudio', '45', '3', 'Junto a la tienda', 3, 'Departamento'),
       (10, '5 de Mayo', '124', 'B', 'Junto a la farmacia', 5, 'Casa');

INSERT INTO Tecnicos (Nombre, Apellido_Paterno, Apellido_Materno, Telefono, Correo, Especialidad, Fecha_Contratacion, Disponibilidad)
VALUES ('Luis', 'Ramirez', 'Soto', '2221110002', 'luis.tec@aguaalerta.com', 'Fontaneria', '2026-01-15', 'Disponible'),
       ('Ana', 'Torres', 'Diaz', '2221110003', 'ana.tec@aguaalerta.com', 'Sensores IoT', '2026-02-01', 'Ocupado');

INSERT INTO Fuentes_Abastecimiento (Nombre_Fuente, Tipo_Fuente, Ubicacion, Capacidad_Suministro_Litros, Descripcion, Estado)
VALUES ('Pozo Cholula 1', 'Pozo', 'Cholula', 500000.00, 'Pozo profundo principal', 'Activo'),
       ('Manantial Atlixco', 'Manantial', 'Atlixco', 300000.00, 'Manantial natural', 'Activo'),
       ('Sistema Municipal Puebla', 'Sistema municipal', 'Puebla', 1000000.00, 'Red municipal', 'Activo');

INSERT INTO Depositos (IDColonia, Nombre_Deposito, Capacidad_Litros, Nivel_Actual_Litros, Tipo_Deposito, Ubicacion, Fecha_Instalacion, Estado)
VALUES (10, 'Tanque Centro', 100000.00, 65000.00, 'Tanque elevado', 'Cholula Centro', '2026-01-20', 'Activo'),
       (11, 'Cisterna La Paz', 50000.00, 8000.00, 'Cisterna', 'Puebla La Paz', '2026-02-10', 'Activo'),
       (12, 'Tanque San Manuel', 80000.00, 30000.00, 'Tanque elevado', 'Puebla San Manuel', '2026-02-15', 'Activo');

INSERT INTO Sensores (IDDeposito, Tipo_Sensor, Marca, Modelo, Fecha_Instalacion, Ultima_Conexion, Estado)
VALUES (10, 'Nivel ultrasonico', 'Siemens', 'SITRANS LU150', '2026-01-25', '2026-11-19', 'Activo'),
       (11, 'Nivel ultrasonico', 'Siemens', 'SITRANS LU150', '2026-02-12', '2026-11-10', 'Desconectado'),
       (12, 'Nivel ultrasonico', 'Honeywell', 'LLE101', '2026-02-20', '2026-11-19', 'Activo');

INSERT INTO Lecturas (IDSensor, Fecha, Hora, Nivel_Agua_Litros, Porcentaje_Llenado, Temperatura, Observaciones)
VALUES (10, '2026-11-19', '08:00', 65000.00, 65.00, 18.50, 'Nivel normal'),
       (11, '2026-11-10', '08:00', 8000.00, 16.00, 19.00, 'Nivel critico, menos del 20%'),
       (12, '2026-11-18', '08:00', 60000.00, 75.00, 17.50, 'Nivel normal'),
       (12, '2026-11-19', '08:00', 30000.00, 37.50, 17.80, 'Bajada repentina, posible fuga');

INSERT INTO Redes_Hidraulicas (Nombre_Red, Tipo_Red, Longitud_Metros, Fecha_Instalacion, Estado, Descripcion)
VALUES ('Red Centro Cholula', 'Primaria', 2500.00, '2020-05-10', 'Activo', 'Red principal del centro'),
       ('Red San Manuel', 'Secundaria', 1800.00, '2018-08-15', 'Mantenimiento', 'Red con fugas recurrentes');

INSERT INTO Tuberias (IDRed, Codigo_Tuberia, Material, Diametro, Longitud_Metros, Fecha_Instalacion, Estado, Ubicacion)
VALUES (10, 'TUB-001', 'PVC', 4.00, 300.00, '2020-05-10', 'Buen estado', 'Calle 5 de Mayo'),
       (11, 'TUB-002', 'Cobre', 2.00, 150.00, '2018-08-15', 'Reparada', 'Av. San Claudio');

INSERT INTO Categorias_Incidencia (Nombre_Categoria, Descripcion, Nivel_Gravedad, Estado)
VALUES ('Fuga de agua', 'Salida de agua en tuberia o deposito', 'Alta', 'Activo'),
       ('Tuberia danada', 'Tuberia rota o en mal estado', 'Alta', 'Activo'),
       ('Falta de suministro', 'No llega agua a la vivienda', 'Media', 'Activo'),
       ('Deposito vacio', 'Deposito sin agua disponible', 'Alta', 'Activo'),
       ('Baja presion', 'El agua llega con poca fuerza', 'Baja', 'Activo'),
       ('Problemas de infraestructura', 'Fallas en redes, valvulas o instalaciones', 'Media', 'Activo');

INSERT INTO Reportes (IDUsuario, IDCategoria, IDTuberia, Titulo, Descripcion, Fecha_Reporte, Hora_Reporte, Ubicacion, Fotografia, Nivel_Prioridad, Estado)
VALUES (14, 10, 11, 'Fuga en la calle', 'Sale agua de la tuberia frente a mi casa', '2026-11-15', '09:30', 'Av. San Claudio 45', 'fotos/reporte10.jpg', 'Alta', 'Resuelto'),
       (13, 12, NULL, 'Sin agua', 'No ha llegado agua en dos dias', '2026-11-18', '07:15', '5 de Mayo 120', 'fotos/reporte11.jpg', 'Media', 'En proceso'),
       (13, 14, 10, 'Poca presion', 'El agua llega con muy poca fuerza', '2026-11-19', '10:40', '5 de Mayo 120', 'fotos/reporte12.jpg', 'Baja', 'Pendiente');

INSERT INTO Reporte_Vivienda (IDReporte, IDVivienda)
VALUES (10, 11),
       (11, 10),
       (11, 12),
       (12, 10);

INSERT INTO Asignaciones (IDReporte, IDTecnico, Fecha_Asignacion, Fecha_Inicio, Fecha_Termino, Observaciones, Estado, Resultado_Atencion)
VALUES (10, 10, '2026-11-15', '2026-11-16', '2026-11-17', 'Se cambio un tramo de tuberia', 'Terminado', 'Fuga reparada'),
       (11, 11, '2026-11-18', '2026-11-19', NULL, 'Se esta revisando el deposito', 'En proceso', 'Sin resultado aun');

INSERT INTO Abastecimientos (IDDeposito, IDFuente, Responsable, Fecha, Hora, Litros_Recibidos)
VALUES (10, 10, 'Carlos Mendez', '2026-11-01', '06:00', 40000.00),
       (11, 12, 'Carlos Mendez', '2026-11-05', '06:30', 20000.00),
       (12, 11, 'Rosa Jimenez', '2026-11-10', '07:00', 30000.00);

INSERT INTO Mantenimientos (IDTecnico, Tipo_Elemento, ID_Elemento, Tipo_Mantenimiento, Fecha, Costo, Observaciones)
VALUES (10, 'Tuberia', 11, 'Correctivo', '2026-11-17', 1500.00, 'Cambio de tramo de tuberia'),
       (11, 'Sensor', 11, 'Preventivo', '2026-11-19', 800.00, 'Revision de sensor desconectado');

INSERT INTO Notificaciones (IDUsuario, Tipo_Origen, ID_Origen, Tipo_Alerta, Mensaje, Fecha, Hora, Estado)
VALUES (10, 'Deposito', 11, 'Nivel bajo', 'Cisterna La Paz por debajo del 20%', '2026-11-10', '08:05', 'Leida'),
       (10, 'Sensor', 11, 'Sensor desconectado', 'El sensor 11 no envia datos', '2026-11-12', '08:00', 'No leida'),
       (10, 'Sensor', 12, 'Fuga detectada', 'Bajada repentina en Tanque San Manuel', '2026-11-19', '08:05', 'No leida'),
       (10, 'Reporte', 12, 'Reporte pendiente', 'El reporte 12 no tiene tecnico asignado', '2026-11-19', '12:00', 'No leida');

-- Revision rapida: deben salir 19 tablas
SELECT table_name FROM information_schema.tables
WHERE table_schema = 'public'
ORDER BY table_name;