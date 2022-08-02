CREATE TABLE pagamentos (
    id uuid NOT NULL,
    valor numeric(19,2) NOT NULL,
    nome varchar(100) NOT NULL,
    numero varchar(19) NOT NULL,
    expiracao varchar(7) NOT NULL,
    codigo varchar(3) NOT NULL,
    status varchar(255) NOT NULL,
    forma_pagamento_id uuid NOT NULL,
    pedido_id uuid NOT NULL,
    data_criacao timestamp without time zone,
    PRIMARY KEY (id)
);