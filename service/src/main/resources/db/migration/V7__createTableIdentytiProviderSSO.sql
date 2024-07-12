-- minhaapi_service.tb_identyti_provider_sso definition

-- Drop table

-- DROP TABLE minhaapi_service.tb_identyti_provider_sso;

CREATE TABLE minhaapi_service.tb_identyti_provider_sso (
	id serial4 NOT NULL,
	alias varchar(255) NULL,
	provider_id varchar(255) NULL,
	CONSTRAINT tb_identyti_provider_sso_pkey PRIMARY KEY (id),
	CONSTRAINT uk_a3vnr4a3i3ibobsn7xxkyt0i2 UNIQUE (alias),
	CONSTRAINT uk_mmlx31c7hm2ms7xiek7vvrb3q UNIQUE (provider_id)
);