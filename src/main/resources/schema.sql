CREATE TABLE IF NOT EXISTS Users(
    id SERIAL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    accountnonlocked BOOLEAN DEFAULT TRUE,
    accountnonexpired BOOLEAN DEFAULT TRUE,
    credentialsnonexpired BOOLEAN DEFAULT TRUE,
    enabled BOOLEAN DEFAULT TRUE,
	created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
	updated_at TIMESTAMPTZ,
    PRIMARY KEY (id)
);