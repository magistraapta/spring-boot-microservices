# Auth Service

This service handles authentication and authorization using Spring Security with JWT tokens.

## Endpoints

### Public Endpoints (No Authentication Required)

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token

### Protected Endpoints (Authentication Required)

- `GET /api/test/hello` - Test endpoint to verify authentication

## Usage

### Register a new user
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com"
  }'
```

### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Access protected endpoint
```bash
curl -X GET http://localhost:8081/api/test/hello \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Configuration

The service uses the following configuration properties:

- `jwt.secret` - Secret key for JWT token signing
- `jwt.expiration` - JWT token expiration time in milliseconds
- `server.port` - Server port (default: 8081) 