-- minhaapi_service.tb_usuario definition

-- Drop table

-- DROP TABLE minhaapi_service.tb_usuario;

CREATE TABLE minhaapi_service.tb_usuario (
	id bigserial NOT NULL,
	email varchar(255) NULL,
	id_usuario_keycloak uuid NULL,
	observacoes bytea NULL,
	status varchar(255) NULL,
	telefone varchar(255) NULL,
	recupera_senha int8 NULL,
	id_endereco int8 NULL,
	CONSTRAINT tb_usuario_pkey PRIMARY KEY (id),
	CONSTRAINT tb_usuario_status_check CHECK (((status)::text = ANY ((ARRAY['ATIVACAO'::character varying, 'ATIVADO_EMAIL'::character varying, 'ATIVADO_SMS'::character varying])::text[]))),
	CONSTRAINT uk_mj464ps9hvd1bqlm6np1j356t UNIQUE (id_endereco),
	CONSTRAINT uk_mo90bmlkrpsh691qoojxyrgvt UNIQUE (telefone),
	CONSTRAINT uk_spmnyb4dsul95fjmr5kmdmvub UNIQUE (email),
	CONSTRAINT fkeinyvv3a9j1msa68uoqjekw5k FOREIGN KEY (id_endereco) REFERENCES minhaapi_service.tb_endereco(id)
);