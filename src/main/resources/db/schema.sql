CREATE TYPE role_type AS ENUM ('ROLE_USER', 'ROLE_ADMIN');

create table if not exists member
(
    id        bigint              not null primary key,
    name      varchar(255)        not null,
    email     varchar(255) unique not null,
    password  varchar(255)        not null,
    role_type role_type           not null
);

create table if not exists concert
(
    id                   bigint       not null primary key,
    title                varchar(255) not null,
    date_time            TIMESTAMP    not null,
    place                varchar(255) not null,
    reservation_open_at  TIMESTAMP    NOT NULL,
    reservation_close_at TIMESTAMP    NOT NULL,
    search_vector        tsvector GENERATED ALWAYS AS (
        to_tsvector('simple', coalesce(title, '') || ' ' || coalesce(place, ''))
        ) STORED
);

CREATE INDEX idx_concert_fts ON concert USING GIN (search_vector);

create table if not exists seat_grade
(
    id         BIGSERIAL PRIMARY KEY,
    concert_id BIGINT  NOT NULL REFERENCES concert (id) ON DELETE CASCADE,
    grade_name CHAR(1) NOT NULL, -- 예: VIP, R, S
    price      BIGINT  NOT NULL  -- 해당 등급의 좌석 가격
);

drop table seat;
create table if not exists seat
(
    id            BIGSERIAL PRIMARY KEY,
    concert_id    BIGINT  NOT NULL REFERENCES concert (id) ON DELETE CASCADE,
    seat_grade_id BIGINT  NOT NULL REFERENCES seat_grade (id) ON DELETE CASCADE,
    row_label     CHAR(1) NOT NULL,
    column_number INT     NOT NULL,
    UNIQUE (concert_id, row_label, column_number)
);

CREATE TYPE reservation_status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED');

create table if not exists reservation
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT             NOT NULL,
    concert_id  BIGINT             NOT NULL,
    seat_id     BIGINT             NOT NULL,
    status      reservation_status NOT NULL,
    reserved_at TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    payment_id  BIGINT,
    CONSTRAINT fk_reservation_seat FOREIGN KEY (seat_id) REFERENCES seat (id)
);

CREATE TYPE payment_status AS ENUM ('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED');

create table if not exists payment
(
    id             BIGSERIAL PRIMARY KEY,
    reservation_id BIGINT         NOT NULL UNIQUE,
    amount         BIGINT         NOT NULL,
    status         payment_status NOT NULL DEFAULT 'PENDING',
    requested_at   TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    paid_at        TIMESTAMP,
    fail_reason    VARCHAR(255),
    pg_provider    VARCHAR(50),
    pg_tid         VARCHAR(100),

    CONSTRAINT fk_payment_reservation FOREIGN KEY (reservation_id) REFERENCES reservation (id)
);

