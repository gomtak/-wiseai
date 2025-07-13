# CONCERT MANIA 백엔드 과제

---

## 프로젝트 개요

- 콘서트 예매 시스템 백엔드 구현 과제
- 회원 가입 → 콘서트 목록 조회 → 좌석 선택 → 예매 및 결제 → 예매 내역 관리
- +@ 대기열 기능

---
## 프로젝트 실행 방법

```shell
docker-compose up -d
./gradlew bootRun --args='--spring.profiles.active=local'

## 앱 종료 후
docker-compose down
```
---

## 기술 스택

- **Language**: Kotlin
- **Framework**: Spring Boot 3.5
- **Database**: PostgreSQL
- **Cache / Lock**: Redis (SpinLock, Queue)
- **Message Broker**: Kafka (미구현)
- **Monitoring**: Prometheus + (미구현)grafana
- **API 문서**: Swagger (http://localhost:8080/swagger-ui.html)
- **Build Tool**: Gradle + Kotlin DSL
- **Architecture**: Hexagonal Architecture (Port & Adapter)

---

## 디렉토리 구조
```
com.concertmania.api
├── auth : 인증/인가 도메인 (JWT 기반)
├── common : 공통 설정, AOP, Redis, 예외 등
├── concert : 콘서트/좌석 도메인
├── member : 회원 도메인
├── notification : 알림 (예정)
├── payment : 결제 도메인
└── reservation : 예매 도메인
```

---

## 핵심 기능
[scenario.http](src/test/http/scenario.http)
* 사용자 관리
  * 회원가입
    * 사용자는 시스템 기능을 이용하기 위해 새로운 계정을 생성할 수 있습니다.
    * 엔드포인트: POST /member
    * Content-Type: application/json
    * 요청 본문 예시:
```json
{
  "name": "tester",
  "email": "test@test.com",
  "password": "123123",
  "roleType": "ROLE_USER"
}
```
  * 로그인
    * 등록된 사용자는 로그인하여 인증 토큰을 발급받을 수 있으며, 이 토큰은 보호된 리소스에 접근하는 데 필요합니다.
    * 엔드포인트: POST /auth/signin
    * Content-Type: application/json
    * 요청 본문 예시:
```json
{
"email": "test@test.com",
"password": "123123"
}
```
* 콘서트 관리
  * 콘서트 목록 조회
    * 사용자는 사용 가능한 콘서트 목록을 조회할 수 있으며, 키워드로 Full text search
    * GET /concert?keyword="sample"
    * 인증: Bearer {{auth_token}}
  * 특정 콘서트 조회
    * 사용자는 콘서트 ID를 사용하여 특정 콘서트의 상세 정보를 조회할 수 있습니다.
    * 엔드포인트: GET /concert/{concertId} (예: GET /concert/802)
    * 인증: Bearer {{auth_token}}
  * 콘서트 대기열 시스템
    * 콘서트 대기열 참여
    * 높은 수요를 관리하기 위해, 사용자는 좌석을 선택하기 전에 특정 콘서트의 대기열에 참여할 수 있습니다.
    * 엔드포인트: POST /concert/{concertId}/queue (예: POST /concert/802/queue)
    * 인증: Bearer {{auth_token}}
  * 콘서트 대기열 상태 조회
    * 사용자는 콘서트 대기열에서의 현재 상태를 확인할 수 있습니다.
    * 엔드포인트: GET /concert/{concertId}/queue/status (예: GET /concert/802/queue/status)
    * 인증: Bearer {{auth_token}}

* 좌석 선택 및 예매
  * 좌석 조회
    * 좌석 정보 조회
    * 엔드포인트: GET /seat/{seatId} (예: GET /seat/665)
    * 인증: Bearer {{auth_token}}
  * 예매 진행
    * 콘서트 대기열 참여 후, 사용자는 특정 콘서트에 대한 예매를 진행할 수 있습니다.
    * 엔드포인트: POST /reservation 
    * 인증: Bearer {{auth_token}} 
    * Content-Type: application/json
    * 요청 본문 예시:
```json
{
    "concertId": 802,
    "seatId": 665
}
```
* 결제 처리
  * 결제 진행 
    * 사용자는 결제를 진행하여 예매를 최종 확정할 수 있습니다.
    * 엔드포인트: POST /payment 
    * 인증: Bearer {{auth_token}} 
    * Content-Type: application/json
    * 요청 본문 예시: 테스트를 위해서 isSuccess로 처리
```json
{
    "reservationId": 1,
    "isSuccess": true 
}
```

---

## 향후 개선 사항 (미구현 기능)
* 목록
  * 콘서트 좌석, 좌석 등급 생성
    * 콘서트 생성시 임의 생성으로 구현
  * 예매 오픈/마감 시간 관리
    * 콘서트 생성시 임의 생성
    * 대기열 획득 및 좌석 예매 기능에서 미구현
  * 결제 처리
    * 임의 필드값으로 성공 실패 처리
    * 외부 라이브러리 모듈화 하여 사용
  * 결제 완료시 예매 확정
    * 결제 완료 이벤트 발행으로 처리
    * 트랜잭션 커밋 이후 이벤트 발생으로 처리
    * kafka 에서 하나의 토픽 payment.completed 생성 후 그룹 아이디로 병렬 처리 (reservation, notification, ...)
  * 캐싱 시스템
    * 쉽게 변경 되지 않고 여러곳에서 조회하는 정보들을 우선적으로 캐싱 ex)concert entity
    * 좌석 상태 캐싱 시 좌석 상태가 변경되는 시점 (예매(스핀락이 걸리는 시점))에 정보 변경
  * DB 인덱싱 최적화
    * 잘 바뀌지 않고 조회하는 정보로 인덱스 생성 ( 공연 날짜 )
    * Text Search Config 변경 등
  * N+1 쿼리 문제 해결
    * QueryDSL 로 구현
  * 커넥션 풀 튜닝
    * 모니터링 도구를 활용해 HikariCP 커넥션 풀 상태(활성, 대기 등)를 지속 관찰 및 알람으로 조정
    * DB 커넥션 부족 발생 시, 리더/라이터 분리 및 읽기 전용 리플리카 활용으로 부하 분산
  * 부하 테스트 및 분석
    * 실제 사용 패턴과 유사한 API 호출 시나리오 작성. 부하 생성기와 대상 서버 환경을 분리 및 제어하여 테스트 수행
    * TPS 측정
    * 병목 지점 탐지는 APM 활용과 슬로우 쿼리 로깅, AOP 기반 메서드 실행 시간 모니터링을 통해 알람 및 로깅 구현
  * +@ 파일 및 폴더 구조 변경 
    * 외부 라이브러리 및 인프라 모듈화
    * MSA로 전환 시 domain별 service 분리 UseCase 로 호출 하는 부분은 FeignClient, Kafka로 통신