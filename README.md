# CONCERT MANIA 백엔드 과제
---

## 1️⃣ 프로젝트 및 환경 설정

- Gradle 의존성 구성 (`Spring Boot 3.5`, `JPA`, `Redis`, `Kafka`, `Prometheus`, `Swagger`, etc.)
- Swagger UI 접속 경로: `http://localhost:8080/swagger-ui.html`
- `application.yml` 환경 설정 구성 (`local`, `dev` 프로파일 분리)
- `docker-compose.yml`에 PostgreSQL, Redis, Kafka, Prometheus 구성
- QueryDSL 설정 (`kapt` + 코드 생성 확인)

---

## 2️⃣ 기본 아키텍처 및 디렉토리 구조 설계

- 헥사고날 아키텍처
- 도메인 분리 설계: `auth`, `common`, `concert`, `member`, `notification`, `payment`, `reservation`
- 각 도메인별 접근은 UseCase로 처리
- 
---
