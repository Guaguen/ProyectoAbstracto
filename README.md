# AguaAlerta API

API REST de **AguaAlerta**, un sistema para monitorear, administrar y dar seguimiento al abastecimiento de agua potable en colonias y comunidades. Permite registrar usuarios y roles, colonias, depósitos de agua y reportes ciudadanos (fugas, falta de agua, daños en tuberías, contaminación), y atender esos reportes en orden de llegada.

Proyecto hecho con **Spring Boot + Gradle (Java 17)**. Los módulos del 3er parcial guardan sus datos **en memoria**, dentro de estructuras de datos propias. La **autenticación** (registro, login y token JWT) guarda los usuarios en **PostgreSQL (Neon)**.

## Integrantes

- Samuel Conde
- Hagen Tellez

## Estructura del proyecto

```
src/main/java/com/example/proyectobd2
├── controllers   → reciben las peticiones y regresan las respuestas
├── services      → lógica, datos en memoria y servicios del login (AuthService, JwtService)
├── repositories  → consultas a Neon con JdbcTemplate (UsuarioRepository)
├── models        → UsuarioMemoria, Rol, Colonia, Deposito, Reporte y Usuario (login)
├── dto           → lo que entra y sale del API (Create...Request, Registro, Login, Mensaje...)
├── security      → token en las rutas, roles y CORS (AuthInterceptor, WebConfig, RequiereRol, Roles)
├── errors        → ManejadorErrores: todos los errores salen como { "message": "..." }
└── structures    → LinkedList y Queue propias
```

## Autenticación

El backend tiene registro, login y consulta del usuario con token JWT. Los usuarios del login se guardan en Neon (tablas `Usuarios` y `Roles`) y las contraseñas se guardan con **BCrypt**.

| Método | Ruta | Token | Respuesta |
|---|---|---|---|
| POST | `/api/auth/registro` | No | 201 con el usuario creado |
| POST | `/api/auth/login` | No | 200 con `token` y `user` |
| GET | `/api/auth/yo` | Sí | 200 con el usuario del token |

- Todas las rutas `/api/**` piden el header `Authorization: Bearer <token>`, menos login, registro y health.
- `/api/usuarios` es solo para **admin** y `/api/depositos` para **admin** y **tecnico**. Si el rol no tiene permiso, responde 403.
- Sin token o con token vencido responde 401.
- El registro no permite crear cuentas de admin. Si no se manda `role`, se asigna `ciudadano`.
- Los errores se responden como `{ "message": "..." }`.

### Crear el primer admin

1. Registrar un usuario normal con `POST /api/auth/registro`.
2. En el SQL Editor de Neon correr:
   ```sql
   UPDATE Usuarios SET IDRol = (SELECT IDRol FROM Roles WHERE Nombre_Rol = 'admin')
   WHERE Correo = 'correo-del-usuario';
   ```
3. Volver a hacer login.

## Módulos y endpoints

### Health (público)

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/health` | `OK` |
| GET | `/api/health/db` | 200 si la consulta `SELECT 1` a la base de datos funciona |

### Usuarios (solo admin)

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/usuarios` | 200 con la lista de usuarios |
| GET | `/api/usuarios/{id}` | 200 con el usuario, o 404 si no existe |
| POST | `/api/usuarios` | 201 con el usuario creado |

### Roles

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/roles` | 200 con la lista de roles |
| GET | `/api/roles/{id}` | 200 con el rol, o 404 si no existe |
| POST | `/api/roles` | 201 con el rol creado |

### Colonias

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/colonias` | 200 con la lista de colonias |
| GET | `/api/colonias/{id}` | 200 con la colonia, o 404 si no existe |
| POST | `/api/colonias` | 201 con la colonia creada |

### Depósitos (admin y tecnico)

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/depositos` | 200 con la lista de depósitos |
| GET | `/api/depositos/{id}` | 200 con el depósito, o 404 si no existe |
| POST | `/api/depositos` | 201 con el depósito creado |

### Reportes

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/reportes` | 200 con la lista de reportes |
| GET | `/api/reportes/{id}` | 200 con el reporte, o 404 si no existe |
| POST | `/api/reportes` | 201 con el reporte creado (entra a la cola de pendientes) |
| GET | `/api/reportes/pendientes` | 200 con la cola de reportes pendientes |
| POST | `/api/reportes/atender` | 200 con el reporte atendido, o 404 si no hay pendientes |

## Estructuras de datos

Las dos estructuras son implementación propia hecha con nodos y están en el paquete `structures`. Cada una tiene un método `toList()` que regresa sus datos como `List` para poder responder en JSON.

| Estructura | Cómo funciona | Dónde se usa |
|---|---|---|
| `LinkedList<T>` | Lista ligada: cada nodo guarda un dato y apunta al siguiente. Métodos `add`, `find`, `remove`, `size`, `toList`. | En **todos los services** de los módulos (`UsuarioService`, `RolService`, `ColoniaService`, `DepositoService`, `ReporteService`) para guardar los registros en memoria. |
| `Queue<T>` | Cola FIFO: el primero que entra es el primero que sale. Métodos `enqueue`, `dequeue`, `peek`, `isEmpty`, `size`, `toList`. | En **`ReporteService`** como cola de reportes pendientes: cada reporte nuevo entra a la cola y `POST /api/reportes/atender` atiende el más antiguo y le cambia el estado a "en atencion". |

Cada service empieza con 3 registros de ejemplo.

## Base de datos

El script de la base está en `sql/aguaalerta_postgresql.sql` (19 tablas con datos de ejemplo). Se corre **una sola vez** en el SQL Editor de Neon. Los usuarios de ejemplo del script no pueden hacer login porque sus contraseñas no son hashes BCrypt; hay que registrar usuarios nuevos.

## Cómo correr el proyecto

1. Clonar el repositorio:
   ```
   git clone git@github.com:Guaguen/ProyectoAbstracto.git
   cd ProyectoAbstracto
   ```
2. Crear el archivo `.env` a partir de `.env.example` y llenar las variables:
   ```
   DB_URL=
   DB_USERNAME=
   DB_PASSWORD=
   JWT_SECRET=
   ```
   `JWT_SECRET` es una cadena inventada de al menos 32 caracteres. `CORS_ORIGENES` solo hace falta al desplegar.
3. Correr el proyecto:
   ```
   ./gradlew bootRun
   ```
   En Windows (PowerShell):
   ```
   .\gradlew.bat bootRun
   ```
4. La API queda en `http://localhost:8080`. Para comprobar que funciona, abre `http://localhost:8080/api/health` y `http://localhost:8080/api/health/db`.

## Colección de Postman

La colección está en la carpeta [`postman/`](postman/) en formato Collection v2.1. Tiene una carpeta por módulo, un body JSON de ejemplo en cada POST y las variables de colección `baseUrl = http://localhost:8080` y `token`.

Para usarla:
1. En Postman, **Import** → selecciona el archivo `.json` de la carpeta `postman`.
2. En la carpeta **Auth** manda **Login admin** (o **Login tecnico**). El token se guarda solo en la variable `token`.
3. Todos los demás requests mandan el token como **Bearer** automáticamente.