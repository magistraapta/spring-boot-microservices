create table if not exists payments (
    id bigint primary key auto_increment,
    order_id bigint not null,
    amount decimal(10, 2) not null,
    payment_method varchar(255) not null,
);
