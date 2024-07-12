ALTER TABLE minhaapi_service.tb_usuario ADD CONSTRAINT tb_usuario_status_check check
(
	(
		(
			(status)::text = ANY
			(array
				[
					('ATIVACAO'::character varying)::text,
					('ATIVADO_EMAIL'::character varying)::text,
					('ATIVADO_SMS'::character varying)::text,
					('ATIVADO_REDE_SOCIAL'::character varying)::text
				]
			)
		)
	)
);