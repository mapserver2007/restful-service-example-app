FROM mariadb:10.4

ENV SCHEMA_DIR com/example/app/infrastructure/schema
ENV DIR /docker-entrypoint-initdb.d

ADD "$SCHEMA_DIR"/db-session-schema.sql "$DIR"/db-session-schema.sql
ADD "$SCHEMA_DIR"/db-social-account-schema.sql "$DIR"/db-social-account-schema.sql