-- Table: public.users

DROP TABLE IF EXISTS public.users CASCADE;
DROP SEQUENCE IF EXISTS users_id_seq;
CREATE SEQUENCE users_id_seq;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL DEFAULT nextval
(
    'users_id_seq'::regclass
),
    create_date timestamp without time zone,
    email character varying
(
    30
) COLLATE pg_catalog."default" NOT NULL,
    firstname character varying
(
    50
) COLLATE pg_catalog."default" NOT NULL,
    lastname character varying
(
    50
) COLLATE pg_catalog."default" NOT NULL,
    password character varying
(
    100
) COLLATE pg_catalog."default" NOT NULL,
    phone character varying
(
    20
) COLLATE pg_catalog."default" NOT NULL,
    username character varying
(
    50
) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY
(
    id
),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE
(
    email
),
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE
(
    username
)
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.users
    OWNER to postgres;


-- Table: public.role

DROP TABLE IF EXISTS public.role CASCADE;
DROP SEQUENCE IF EXISTS role_id_seq;
CREATE SEQUENCE role_id_seq;

CREATE TABLE IF NOT EXISTS public.role
(
    id bigint NOT NULL DEFAULT nextval
(
    'role_id_seq'::regclass
),
    create_date timestamp without time zone,
    name character varying
(
    20
) COLLATE pg_catalog."default",
    CONSTRAINT role_pkey PRIMARY KEY
(
    id
)
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.role
    OWNER to postgres;


-- Table: public.user_role

DROP TABLE IF EXISTS public.user_role CASCADE;

CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id
    bigint
    NOT
    NULL,
    role_id
    bigint
    NOT
    NULL,
    CONSTRAINT
    user_role_pkey
    PRIMARY
    KEY
(
    user_id,
    role_id
),
    CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY
(
    role_id
)
    REFERENCES public.role
(
    id
) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION,
    CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY
(
    user_id
)
    REFERENCES public.users
(
    id
) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.user_role
    OWNER to postgres;


-- Table: public.list

DROP TABLE IF EXISTS public.list CASCADE;
DROP SEQUENCE IF EXISTS list_id_seq;
CREATE SEQUENCE list_id_seq;

CREATE TABLE IF NOT EXISTS public.list
(
    id bigint NOT NULL DEFAULT nextval('list_id_seq'::regclass),
    create_date date,
    description character varying(50) COLLATE pg_catalog."default" NOT NULL,
    user_model_id bigint,
    CONSTRAINT list_pkey PRIMARY KEY (id),
    CONSTRAINT fkk6lh163kns8t1720weg4fh9k2 FOREIGN KEY (user_model_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.list
    OWNER to postgres;


-- Table: public.todo

DROP TABLE IF EXISTS public.todo CASCADE;
DROP SEQUENCE IF EXISTS todo_id_seq;
CREATE SEQUENCE todo_id_seq;

CREATE TABLE IF NOT EXISTS public.todo
(
    id bigint NOT NULL DEFAULT nextval
(
    'todo_id_seq'::regclass
),
    create_date date,
    description character varying
(
    50
) COLLATE pg_catalog."default" NOT NULL,
    deadline date,
    list_id bigint,
    CONSTRAINT todo_pkey PRIMARY KEY
(
    id
),
    CONSTRAINT uk_m22jy2glcboqg0s9cng1jfrnc UNIQUE
(
    description
),
    CONSTRAINT fkm5ml2kpsbgm88eac9l9dqtr0h FOREIGN KEY
(
    list_id
)
    REFERENCES public.list
(
    id
) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.todo
    OWNER to postgres;

-- Table: public.sub_item

DROP TABLE IF EXISTS public.sub_item CASCADE;
DROP SEQUENCE IF EXISTS sub_item_id_seq;
CREATE SEQUENCE sub_item_id_seq;

CREATE TABLE IF NOT EXISTS public.sub_item
(
    id bigint NOT NULL DEFAULT nextval
(
    'sub_item_id_seq'::regclass
),
    create_date date,
    description character varying
(
    50
) COLLATE pg_catalog."default" NOT NULL,
    todo_id bigint,
    CONSTRAINT sub_item_pkey PRIMARY KEY
(
    id
),
    CONSTRAINT uk_sbw4el8m1e2jd02sdk9osaynw UNIQUE
(
    description
),
    CONSTRAINT fki2fawu0t4x0mrgfo8miprx1ss FOREIGN KEY
(
    todo_id
)
    REFERENCES public.todo
(
    id
) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    )
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sub_item
    OWNER to postgres;


INSERT INTO public.role(id, create_date, name)
VALUES (1, CURRENT_TIMESTAMP, 'ADMIN');

INSERT INTO public.role(id, create_date, name)
VALUES (2, CURRENT_TIMESTAMP, 'USER');
