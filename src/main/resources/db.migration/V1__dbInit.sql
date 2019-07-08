create table if not exists shopping_cart
(
  id char(36) not null constraint shopping_cart_pkey primary key,
  json_document jsonb not null
);