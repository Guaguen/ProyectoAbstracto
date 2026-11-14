# AguaAlerta API

API REST de **AguaAlerta**, un sistema para monitorear, administrar y dar seguimiento al abastecimiento de agua potable en colonias y comunidades. Permite registrar usuarios y roles, colonias, depósitos de agua y reportes ciudadanos (fugas, falta de agua, daños en tuberías, contaminación), y atender esos reportes en orden de llegada.

Proyecto del 3er parcial hecho con **Spring Boot + Gradle (Java 17)**. En este parcial los datos viven **en memoria**, dentro de estructuras de datos propias; de la base de datos (PostgreSQL en Neon) solo se hace la conexión y se comprueba desde un endpoint.

## Integrantes

- Samuel Conde
- Hagen Tellez

## Estructura del proyecto

```
src/main/java/com/example/proyectobd2
├── controllers   → reciben las peticiones y regresan las respuestas
├── services      → lógica y datos en memoria (en las estructuras)
├── models        → Usuario, Rol, Colonia, Deposito, Reporte
├── dto           → lo que manda el cliente (Create...Request, sin id)
└── structures    → LinkedList y Queue propias
```

## Módulos y endpoints

### Health

| Método | Ruta | Respuesta |
|---|---|---|
| GET | `/api/health` | `OK` |
| GET | `/api/health/db` | 200 si la consulta `SELECT 1` a la base de datos funciona |

### Usuarios

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

### Depósitos

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
| `LinkedList<T>` | Lista ligada: cada nodo guarda un dato y apunta al siguiente. Métodos `add`, `find`, `remove`, `size`, `toList`. | En **todos los services** (`UsuarioService`, `RolService`, `ColoniaService`, `DepositoService`, `ReporteService`) para guardar los registros en memoria. |
| `Queue<T>` | Cola FIFO: el primero que entra es el primero que sale. Métodos `enqueue`, `dequeue`, `peek`, `isEmpty`, `size`, `toList`. | En **`ReporteService`** como cola de reportes pendientes: cada reporte nuevo entra a la cola y `POST /api/reportes/atender` atiende el más antiguo y le cambia el estado a "en atencion". |

Cada service empieza con 3 registros de ejemplo.

## Cómo correr el proyecto

1. Clonar el repositorio:
   ```
   git clone git@github.com:Guaguen/ProyectoAbstracto.git
   cd ProyectoAbstracto
   ```
2. Crear el archivo `.env` a partir de `.env.example` y llenar las variables con los datos de la base de datos:
   ```
   DB_URL=
   DB_USERNAME=
   DB_PASSWORD=
   ```
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

La colección está en la carpeta [`postman/`](postman/) en formato Collection v2.1. Tiene una carpeta por módulo, un body JSON de ejemplo en cada POST y la variable de colección `baseUrl = http://localhost:8080`.

Para usarla: en Postman, **Import** → selecciona el archivo `.json` de la carpeta `postman`.