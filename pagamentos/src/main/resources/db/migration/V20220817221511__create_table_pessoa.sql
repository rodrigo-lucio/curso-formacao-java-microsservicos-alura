CREATE TABLE pessoa (
    id uuid NOT NULL,
    nome varchar(255) NOT NULL,
    tipo_pessoa varchar(255) NOT NULL,
    cpf_cnpj varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    criado_em timestamp,
    atualizado_em timestamp,
    CONSTRAINT pk_pessoa_id PRIMARY KEY (id),
    CONSTRAINT uk_pessoa_cpf_cpnj UNIQUE (cpf_cnpj)
);