USE s7673_paneladb;

CREATE TABLE IF NOT EXISTS allowed_users (
	id int primary key auto_increment,
    name varchar(64) not null
);

CREATE TABLE IF NOT EXISTS users_login (
	id int primary key auto_increment,
    created_at timestamp default now(),
    name varchar(64) not null,
    password text
)

SELECT * FROM allowed_users;