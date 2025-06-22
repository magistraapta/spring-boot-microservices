create table users (
    id serial primary key,
    username varchar(255) not null unique,
    password varchar(255) not null
);

insert into users (username, password) values ('admin', 'admin');
