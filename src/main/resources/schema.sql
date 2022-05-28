CREATE TABLE IF NOT EXISTS users (
    id
    SERIAL
    PRIMARY
    KEY,
    username
    varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);

-- An INTEGER PRIMARY KEY becomes the actual key used in the B-tree that stores your table. So no index is required for efficient operation.
CREATE UNIQUE INDEX IF NOT EXISTS name_index ON users(username);

CREATE TABLE IF NOT EXISTS tokens (
    token VARCHAR(36) PRIMARY KEY,
    user_id INTEGER NOT NULL,
    timestamp TIMESTAMPTZ DEFAULT NOW() NOT NULL
);
