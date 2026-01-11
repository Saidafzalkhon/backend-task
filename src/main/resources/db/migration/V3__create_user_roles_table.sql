create table user_roles
(
    id         bigserial primary key,
    user_id    bigint      not null,
    role_id    bigint      not null,
    created_at timestamptz not null default now(),
    updated_at timestamptz,

    constraint fk_user_roles_user
        foreign key (user_id)
            references users (id)
            on delete cascade,

    constraint fk_user_roles_role
        foreign key (role_id)
            references roles (id)
            on delete cascade,

    constraint uk_user_role
        unique (user_id, role_id)
);
