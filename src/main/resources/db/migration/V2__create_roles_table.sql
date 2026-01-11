create table roles
(
    id         bigserial primary key,
    name       varchar(255) not null unique,
    created_at timestamp    not null default now(),
    updated_at timestamp
);