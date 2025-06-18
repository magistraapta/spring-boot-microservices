create table if not exists orders (
    id serial primary key,
    product_id bigint not null,
    quantity int not null
);