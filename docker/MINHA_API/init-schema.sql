DO $$
BEGIN
    IF NOT EXISTS(
        SELECT schema_name
        FROM information_schema.schemata
        WHERE schema_name = 'minhaApi_schema'
    ) THEN
        EXECUTE 'CREATE SCHEMA minhaApi_schema';
    END IF;
END
$$;