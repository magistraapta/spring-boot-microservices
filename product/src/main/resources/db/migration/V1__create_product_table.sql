create table products (
    id serial primary key,
    name varchar(255) not null,
    description varchar(255) not null,
    price decimal(10,2) not null,
    quantity integer not null
);