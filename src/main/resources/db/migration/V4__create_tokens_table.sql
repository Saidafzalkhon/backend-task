create table tokens (
                        id           bigserial primary key,
                        token        varchar(255) not null,
                        token_type   varchar(50) not null,
                        user_id      bigint not null,
                        created_at   timestamptz not null default now(),
                        updated_at   timestamptz,

                        constraint fk_tokens_user
                            foreign key (user_id)
                                references users(id)
                                on delete cascade
);

create index idx_tokens_token on tokens(token);
create index idx_tokens_user_type on tokens(user_id, token_type);
