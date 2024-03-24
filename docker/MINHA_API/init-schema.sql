DO $$
BEGIN
    IF NOT EXISTS(
        SELECT schema_name
        FROM information_schema.schemata
        WHERE schema_name = 'minhaApi_keycloack'
    ) THEN
        EXECUTE 'CREATE SCHEMA minhaApi_keycloack';
    END IF;

     IF NOT EXISTS(
        SELECT schema_name
        FROM information_schema.schemata
        WHERE schema_name = 'minhaApi_service'
    ) THEN
        EXECUTE 'CREATE SCHEMA minhaApi_service';
    END IF;
END
$$;