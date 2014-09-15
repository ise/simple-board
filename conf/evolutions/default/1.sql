# Responses schema
# Threads schema

# --- !Ups

CREATE TABLE response (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    message varchar(140) NOT NULL,
    user_id varchar(255) NOT NULL,
    thread_id bigint(20) NOT NULL,
    created_at timestamp NOT NULL,
    deleted_at timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE thread (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(140) NOT NULL,
    user_id varchar(255) NOT NULL,
    is_closed int NOT NULL DEFAULT 0,
    created_at timestamp NOT NULL,
    deleted_at timestamp,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE response;
DROP TABLE thread;
