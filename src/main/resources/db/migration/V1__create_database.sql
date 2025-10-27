CREATE TABLE users (
    user_id VARCHAR2(36) PRIMARY KEY,
    name VARCHAR2(255) NOT NULL,
    document_number VARCHAR2(18) NOT NULL,
    phone_number VARCHAR2(20),
    birth_date DATE NOT NULL,
    email VARCHAR2(255) NOT NULL,
    password_hash VARCHAR2(255) NOT NULL,
    role VARCHAR2(255) NOT NULL,
    investor_level VARCHAR2(20),
    CONSTRAINT chk_investor_level CHECK (investor_level IN ('iniciante', 'moderado', 'avancado', 'profissional')),
    CONSTRAINT uq_user_document UNIQUE (document_number),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE accounts (
    account_id VARCHAR2(36) PRIMARY KEY,
    user_id VARCHAR2(36) NOT NULL,
    account_name VARCHAR2(255) NOT NULL,
    balance NUMBER(12,2) DEFAULT 0 NOT NULL,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE operations (
    operation_id VARCHAR2(36) PRIMARY KEY,
    account_id VARCHAR2(36) NOT NULL,
    description VARCHAR2(255),
    operation_type VARCHAR2(20) NOT NULL,
    value NUMBER(12,2) NOT NULL,
    occured_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_operation_type CHECK (operation_type IN ('deposit', 'withdraw')),
    CONSTRAINT chk_operation_value CHECK (value > 0),
    CONSTRAINT fk_operation_account FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);

CREATE TABLE assets (
    asset_id VARCHAR2(36) PRIMARY KEY,
    symbol CHAR(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    asset_type VARCHAR2(20) NOT NULL,
    current_price NUMBER(12,2) NOT NULL,
    market_sector VARCHAR2(255),
    market_cap NUMBER(20,4),
    CONSTRAINT chk_asset_type CHECK (asset_type IN ('crypto', 'stock', 'currencies')),
    CONSTRAINT uq_asset_symbol UNIQUE (symbol)
);

CREATE TABLE investments (
    investment_id VARCHAR2(36) PRIMARY KEY,
    account_id VARCHAR2(36) NOT NULL,
    asset_id VARCHAR2(36) NOT NULL,
    amount NUMBER(16,8) NOT NULL,
    purchase_price NUMBER(12,2) NOT NULL,
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_investment_amount CHECK (amount > 0),
    CONSTRAINT chk_investment_price CHECK (purchase_price > 0),
    CONSTRAINT fk_investment_account FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_investment_asset FOREIGN KEY (asset_id) REFERENCES assets(asset_id),
    CONSTRAINT uq_investment_asset UNIQUE (account_id, asset_id)
);

CREATE TABLE courses (
    course_id VARCHAR2(36) PRIMARY KEY,
    title VARCHAR2(255) NOT NULL,
    summary VARCHAR2(255),
    description VARCHAR2(500),
    level VARCHAR2(20),
    thumbnail VARCHAR2(500),
    created_at TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT chk_course_level CHECK (level IN ('basico', 'intermediario', 'avancado')),
    CONSTRAINT uq_course_title UNIQUE (title)
);

CREATE TABLE lessons (
    lesson_id VARCHAR2(36) PRIMARY KEY,
    course_id VARCHAR2(36) NOT NULL,
    title VARCHAR2(255) NOT NULL,
    video_url VARCHAR2(500) NOT NULL,
    duration_in_sec NUMBER NOT NULL,
    lesson_order NUMBER DEFAULT 1 NOT NULL,
    CONSTRAINT fk_lesson_course FOREIGN KEY (course_id) REFERENCES courses(course_id),
    CONSTRAINT uq_lesson_order UNIQUE (course_id, lesson_order)
);

CREATE TABLE account_lesson_progress (
    account_id VARCHAR2(36) NOT NULL,
    lesson_id VARCHAR2(36) NOT NULL,
    current_time_sec NUMBER NOT NULL DEFAULT 0,
    completed NUMBER(1) DEFAULT 0,
    completed_at TIMESTAMP(6),
    PRIMARY KEY (account_id, lesson_id),
    CONSTRAINT chk_progress_completed CHECK (completed IN (0, 1)),
    CONSTRAINT fk_progress_user FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    CONSTRAINT fk_progress_lesson FOREIGN KEY (lesson_id) REFERENCES lessons(lesson_id)
);