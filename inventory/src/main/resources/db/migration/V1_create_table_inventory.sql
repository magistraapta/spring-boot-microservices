create table inventory (
    id serial primary key,
    sku_code varchar(255) not null,
    quantity int not null
);