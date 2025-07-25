# PgAdmin

To connect PgAdmin to a PostgreSQL database, I needed to put `host.docker.internal` as the host in the connection settings. This allows PgAdmin to connect to the PostgreSQL server running on the host machine from within the Docker container.