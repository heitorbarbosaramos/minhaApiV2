-- minhaapi_service.tb_endereco definition

-- Drop table

-- DROP TABLE minhaapi_service.tb_endereco;

CREATE TABLE minhaapi_service.tb_endereco (
	id bigserial NOT NULL,
	bairro varchar(255) NULL,
	cep varchar(255) NULL,
	complemento varchar(255) NULL,
	ddd varchar(255) NULL,
	gia varchar(255) NULL,
	ibge varchar(255) NULL,
	localidade varchar(255) NULL,
	logradouro varchar(255) NULL,
	numero varchar(255) NULL,
	uf varchar(255) NULL,
	CONSTRAINT tb_endereco_pkey PRIMARY KEY (id)
);