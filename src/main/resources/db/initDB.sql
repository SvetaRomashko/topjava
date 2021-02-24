DROP TABLE IF EXISTS user_roles;
drop table if exists meals;
DROP TABLE IF EXISTS users;
Drop index if exists user_id_dateTime_index;
DROP SEQUENCE IF EXISTS global_seq;


CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE MEALS
(
    user_id       INTEGER                             NOT NULL,
    dateTime      timestamp          DEFAULT now()  not null,
    description   VARCHAR                           not null,
    calories      INTEGER                           not null,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
create unique index user_id_dateTime_index on  MEALS(user_id,dateTime);