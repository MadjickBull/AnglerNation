# AnglerNation Backend

Spring Boot 3.3 REST API for the AnglerNation fishing social application.

## Overview

AnglerNation is a platform for anglers to log catches, manage gear, discover fishing locations, and connect with the fishing community.

## Tech Stack

- **Framework**: Spring Boot 3.3
- **Language**: Java 21
- **Build**: Maven
- **Database**: PostgreSQL (primary), Redis (caching)
- **Migration**: Flyway
- **Authentication**: JWT (RS256) with refresh token rotation
- **Authorization**: Role-based access control (ANGLER, ADMIN, MODERATOR)
- **API Documentation**: OpenAPI 3.0 / Swagger UI
- **Testing**: JUnit 5, Mockito, Testcontainers

## Project Structure

```
src/main/java/com/anglernation/
├── config/                 # Configuration classes
│   ├── SecurityConfig.java
│   ├── OpenApiConfig.java
│   ├── OAuth2Config.java
│   └── RateLimitConfig.java
├── controller/             # REST Controllers
│   ├── AuthController.java
│   ├── CatchController.java
│   ├── FishController.java
│   ├── GearController.java
│   ├── LocationController.java
│   ├── PostController.java
│   └── UserController.java
├── dto/                    # Data Transfer Objects (records)
│   ├── auth/
│   ├── catchs/
│   ├── fish/
│   ├── gear/
│   ├── location/
│   └── social/
├── entity/                 # JPA Entities
│   ├── User.java
│   ├── Catch.java
│   ├── Fish.java
│   ├── Gear.java
│   ├── Location.java
│   └── Post.java
├── exception/              # Custom exceptions
│   └── GlobalExceptionHandler.java
├── mapper/                 # MapStruct mappers
├── repository/             # JPA Repositories
├── security/               # Security components
│   ├── JwtTokenProvider.java
│   ├── JwtAuthenticationFilter.java
│   └── RefreshToken.java
└── service/                # Business logic
    ├── AuthService.java
    ├── CatchService.java
    ├── FishService.java
    ├── GearService.java
    ├── LocationService.java
    ├── PostService.java
    └── UserService.java
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout (revoke refresh token)
- `POST /api/auth/forgot-password` - Initiate password reset
- `POST /api/auth/reset-password` - Reset password with token
- `GET /api/auth/me` - Get current user profile

### Catches
- `GET /api/v1/catches` - List catches (paginated)
- `GET /api/v1/catches/{id}` - Get catch by ID
- `POST /api/v1/catches` - Create catch
- `PUT /api/v1/catches/{id}` - Update catch
- `DELETE /api/v1/catches/{id}` - Delete catch

### Gear (Rods, Lures, Lines)
- `GET /api/v1/rods` - List rods
- `POST /api/v1/rods` - Create rod
- `GET /api/v1/lures` - List lures
- `POST /api/v1/lures` - Create lure
- `GET /api/v1/lines` - List lines
- `POST /api/v1/lines` - Create line

### Locations
- `GET /api/v1/locations` - List locations
- `POST /api/v1/locations` - Create location

### Fish
- `GET /api/v1/fish` - List fish species

### Social (Posts, Comments)
- `GET /api/v1/posts` - List posts
- `POST /api/v1/posts` - Create post
- `GET /api/v1/posts/{id}/comments` - Get comments
- `POST /api/v1/posts/{id}/comments` - Create comment

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL 16+
- Redis 7+

### Configuration

Create `src/main/resources/application-local.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/anglernation
    username: anglernation
    password: your_password
  redis:
    host: localhost
    port: 6379
  flyway:
    locations: classpath:db/migration

app:
  jwt:
    access-token-validity-minutes: 15
    refresh-token-validity-days: 30
    issuer: anglernation
```

### Running Locally

```bash
# Build
./mvnw clean compile

# Run tests
./mvnw test

# Run integration tests (requires Testcontainers)
./mvnw verify

# Start application
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Application starts at `http://localhost:8080`

### API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Database Migrations

Flyway migrations in `src/main/resources/db/migration/`:
- `V1__init_schema.sql` - Initial schema
- `V2__add_auth_tables.sql` - Auth tables
- `V3__add_social_tables.sql` - Posts, comments

Run migrations:
```bash
./mvnw flyway:migrate
```

## Security

- JWT RS256 asymmetric signing
- Access tokens: 15 minutes
- Refresh tokens: 30 days with rotation & reuse detection
- BCrypt password hashing (cost 12)
- Rate limiting on auth endpoints (5 req/min)
- CORS configured for frontend origins
- Security headers via Spring Security

## Testing

```bash
# Unit tests
./mvnw test

# Integration tests (PostgreSQL via Testcontainers)
./mvnw verify

# Coverage report
./mvnw jacoco:report

# Security scans
./mvnw spotbugs:check
./mvnw org.owasp:dependency-check-maven:check
```

## Deployment

### Docker
```bash
# Build image
docker build -t anglernation-backend .

# Run
docker run -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/anglernation \
  anglernation-backend
```

### Environment Variables (Production)
```
SPRING_PROFILES_ACTIVE=prod
SPRING_DATASOURCE_URL=jdbc:postgresql://host:5432/anglernation
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
SPRING_REDIS_HOST=redis
SPRING_REDIS_PORT=6379
APP_JWT_PRIVATE_KEY=...
APP_JWT_PUBLIC_KEY=...
```

## Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

## License

MIT License - see LICENSE file for details.

## Links

- Frontend (Android): [AnglerNation-Android](../AnglerNation-Android)
- API Documentation: `/swagger-ui.html`
- Issues: GitHub Issues