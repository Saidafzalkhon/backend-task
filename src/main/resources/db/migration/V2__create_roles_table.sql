create table roles
(
    id         bigserial primary key,
    name       varchar(255) not null unique,
    created_at TIMESTAMP    not null default now(),
    updated_at TIMESTAMP
);