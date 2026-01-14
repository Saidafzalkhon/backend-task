INSERT INTO public.users (id, username, email, full_name, password_hash, is_active, created_at, updated_at) VALUES (1, 'salom', 'salom@gmail.com', 'hello world', '$2y$10$Oxc0v2NwSD3iP5BWQBG.COzAyhYUS6iiNGsS..u.QGCRvGbyLtfZq', true, '2026-01-12 18:41:20.909993', null);
INSERT INTO public.roles (id, name, created_at, updated_at) VALUES (1, 'ADMIN', '2026-01-12 18:42:03.304955', null);
INSERT INTO public.user_roles (id, user_id, role_id, created_at, updated_at) VALUES (1, 1, 1, '2026-01-12 18:42:10.558019', null);
