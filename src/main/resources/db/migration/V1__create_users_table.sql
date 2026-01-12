create table users
(
    id            bigserial primary key,
    username      varchar(255) not null unique,
    email         varchar(255) not null unique,
    full_name     varchar(255) not null,
    password_hash varchar(255) not null,
    is_active     boolean      not null default true,
    created_at    TIMESTAMP    not null default now(),
    updated_at    TIMESTAMP
);