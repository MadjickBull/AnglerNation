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
├── config/                 # Configuration classes (planned)
├── controller/             # REST Controllers
│   └── AuthController.java ✅
├── dto/                    # Data Transfer Objects (records)
│   └── auth/               ✅ LoginRequest, RegisterRequest, TokenResponse, RefreshTokenRequest
├── entity/                 # JPA Entities
│   ├── User.java           ✅
│   ├── Rod.java            ✅
│   ├── Lure.java           ✅
│   ├── Line.java           ✅
│   ├── Location.java       ✅
│   ├── Fish.java           ✅
│   ├── Catch.java          ✅
│   ├── Post.java           ✅
│   └── Comment.java        ✅
├── enums/                  # Enumeration types
│   ├── RodType.java        ✅
│   ├── RodPower.java       ✅
│   ├── RodAction.java      ✅
│   ├── LureType.java       ✅
│   ├── LureBuoyancy.java   ✅
│   ├── LineType.java       ✅
│   ├── LocationVisibility.java ✅
│   └── FishType.java       ✅
├── exception/              # Custom exceptions
│   └── (planned: GlobalExceptionHandler.java)
├── mapper/                 # MapStruct mappers (planned)
├── repository/             # JPA Repositories
│   ├── UserRepo.java       ✅
│   ├── RodRepo.java        ✅
│   ├── LureRepo.java       ✅
│   ├── LineRepo.java       ✅
│   ├── LocationRepo.java   ✅
│   ├── FishRepo.java       ✅
│   └── CatchRepo.java      ✅
├── security/               # Security components
│   ├── JwtTokenProvider.java      ✅
│   ├── JwtAuthenticationFilter.java ✅
│   ├── CustomJwtAuthenticationConverter.java ✅
│   ├── RefreshToken.java         ✅
│   ├── RefreshTokenRepository.java ✅
│   ├── JwtAuthenticationException.java ✅
│   └── SecurityConfig.java       ✅
└── service/                # Business logic
    ├── AuthService.java    ✅
    └── UserService.java    ✅ (partial)
```

**Legend**: ✅ = Implemented | 🟡 = Partial | ⬜ = Planned

## Current Implementation Status

| Domain | Entities | Enums | Repository | Service | Controller | DTOs | Mapper |
|--------|----------|-------|------------|---------|------------|------|--------|
| **Auth** | - | - | - | ✅ AuthService | ✅ AuthController | ✅ auth/* | - |
| **User** | ✅ User | - | ✅ UserRepo | 🟡 UserService | ⬜ | 🟡 Create/UpdateUserRequest | ⬜ |
| **Rods** | ✅ Rod | ✅ RodType, RodPower, RodAction | ✅ RodRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Lures** | ✅ Lure | ✅ LureType, LureBuoyancy | ✅ LureRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Lines** | ✅ Line | ✅ LineType | ✅ LineRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Locations** | ✅ Location | ✅ LocationVisibility | ✅ LocationRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Fish** | ✅ Fish | ✅ FishType | ✅ FishRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Catches** | ✅ Catch | - | ✅ CatchRepo | ⬜ | ⬜ | ⬜ | ⬜ |
| **Posts** | ✅ Post | - | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |
| **Comments** | ✅ Comment | - | ⬜ | ⬜ | ⬜ | ⬜ | ⬜ |

## Implemented API Endpoints

### Authentication (✅ Complete)
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - Logout (revoke refresh token)
- `GET /api/auth/me` - Get current user profile

### User (🟡 Partial)
- User registration via auth endpoint
- User profile retrieval via `/api/auth/me`

### Other Domains (⬜ Not Yet Implemented)
- **Catches**: CRUD endpoints planned
- **Gear (Rods, Lures, Lines)**: CRUD endpoints planned
- **Locations**: CRUD endpoints planned
- **Fish**: List species endpoint planned
- **Social (Posts, Comments)**: CRUD endpoints planned

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

- Swagger UI: `http://localhost:8080/swagger-ui.html` (after OpenApiConfig is added)
- OpenAPI JSON: `http://localhost:8080/v3/api-docs` (after OpenApiConfig is added)

## Database Migrations

Flyway migrations in `src/main/resources/db/migration/`:
- `V1__init_schema.sql` - Initial schema (User, Rod, Lure, Line, Location, Fish, Catch)
- `V2__create_refresh_tokens_table.sql` - Refresh tokens table
- `V3__add_social_tables.sql` - Posts, comments tables

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
- API Documentation: `/swagger-ui.html` (when available)
- Issues: GitHub Issues
