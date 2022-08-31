CREATE TABLE pedido (
  id uuid NOT NULL,
  data_emissao date NOT NULL,
  status varchar(255) NOT NULL,
  criado_em timestamp,
  atualizado_em timestamp,
  CONSTRAINT pk_pedido_id PRIMARY KEY (id)
);
