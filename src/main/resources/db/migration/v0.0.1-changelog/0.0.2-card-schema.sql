CREATE TABLE cards (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    card_number VARCHAR(16) NOT NULL UNIQUE,
    card_secret VARCHAR(3) NOT NULL,
    card_owner_name VARCHAR(255),
    status VARCHAR(18) NOT NULL DEFAULT 'ENABLED',
    deleted BOOLEAN NOT NULL  DEFAULT FALSE,
    balance DECIMAL(19,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    expiry_date INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cards_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT valid_status CHECK (status IN ('ENABLED', 'DISABLED', 'EXPIRED', 'PENDING_ACTIVATION'))
);