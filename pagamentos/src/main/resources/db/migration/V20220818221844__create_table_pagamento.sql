CREATE TABLE pagamento (
    id uuid NOT NULL,
    valor numeric(19,2) NOT NULL,
    nome varchar(100) NOT NULL,
    numero varchar(19) NOT NULL,
    expiracao varchar(7) NOT NULL,
    codigo varchar(3) NOT NULL,
    status varchar(255) NOT NULL,
    forma_pagamento_id uuid NOT NULL,
    pedido_id uuid NOT NULL,
    criado_em timestamp,
    atualizado_em timestamp,
    CONSTRAINT pk_pagamento_id PRIMARY KEY (id)
);
