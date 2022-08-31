CREATE TABLE item_pedido (
  id uuid NOT NULL,
  descricao varchar(255) NOT NULL,
  quantidade numeric(19) NOT NULL,
  pedido_id uuid NOT NULL,
  CONSTRAINT pk_item_pedido_id PRIMARY KEY (id),
  CONSTRAINT fk_pedido_id FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);
