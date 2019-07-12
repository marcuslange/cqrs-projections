create table if not exists shopping_cart
(
  id char(36) not null constraint shopping_cart_pkey primary key,
  json_document jsonb not null
);

create table if not exists prj_shopping_cart
(
    id char(36) not null constraint prj_shopping_cart_pk primary key,
    subtotal double precision
);

create table if not exists prj_line_item
(
    shopping_cart_id char(36) not null constraint prj_line_item_prj_shopping_cart_id_fk references prj_shopping_cart on delete cascade,
    product_id varchar(256) not null,
    product_name varchar(256) not null,
    product_price double precision not null,
    quantity integer not null,
    constraint prj_line_item_pk primary key (shopping_cart_id, product_id)
);

