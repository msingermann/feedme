CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    last_name varchar(80),
    name varchar(80),
    phone varchar(40)
);

CREATE UNIQUE INDEX IF NOT EXISTS name_index ON users(email);

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
    CONSTRAINT fk_feeders_user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS annexes (
    id UUID PRIMARY KEY,
    port CHAR NOT NULL,
    type varchar(20) NOT NULL,
    deposit INT,
    bowl INT,
    feeder_id UUID NOT NULL,

    CONSTRAINT fk_annexes_feeders_id FOREIGN KEY(feeder_id) REFERENCES feeders(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS pets (
    id UUID PRIMARY KEY,
    name varchar(255) NOT NULL,
    tag varchar(255) NOT NULL,
    user_id INT NOT NULL,
    annex_id UUID, -- NOT NULL

    CONSTRAINT fk_pets_user_id FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_pets_annexes_id FOREIGN KEY(annex_id) REFERENCES annexes(id) ON DELETE CASCADE
);
