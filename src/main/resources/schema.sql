CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS name_index ON users(username);

CREATE TABLE IF NOT EXISTS tokens (
    token UUID PRIMARY KEY,
    user_id INT NOT NULL,
    timestamp TIMESTAMPTZ DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS feeders (
    id UUID PRIMARY KEY,
    mac varchar(255) NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY(user_id) REFERENCES users(id)
);