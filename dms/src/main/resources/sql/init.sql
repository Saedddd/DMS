-- Проверяем существование базы данных 'dms'
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'dms')
BEGIN
    -- Создаем базу данных dms
    CREATE DATABASE dms;
END
GO

-- Используем базу данных dms
USE dms;
GO

-- Создаем схемы таблиц, если они не существуют
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'dms')
BEGIN
    EXEC('CREATE SCHEMA dms');
END
GO

-- Создаем таблицы только если они не существуют
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'roles' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE roles (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        name NVARCHAR(50) NOT NULL UNIQUE
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'users' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE users (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        full_name NVARCHAR(100) NOT NULL,
        email NVARCHAR(100) NOT NULL UNIQUE,
        date_of_birth DATE NULL,
        role_id UNIQUEIDENTIFIER NULL,
        FOREIGN KEY (role_id) REFERENCES roles(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'statuses' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE statuses (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        name NVARCHAR(50) NOT NULL UNIQUE,
        is_final BIT NOT NULL DEFAULT 0,
        order_index INT NOT NULL
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'documents' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE documents (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        title NVARCHAR(255) NOT NULL,
        description NVARCHAR(MAX) NULL,
        status_id UNIQUEIDENTIFIER NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        updated_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        owner_id UNIQUEIDENTIFIER NOT NULL,
        FOREIGN KEY (status_id) REFERENCES statuses(id),
        FOREIGN KEY (owner_id) REFERENCES users(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'attachments' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE attachments (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        file_name NVARCHAR(255) NOT NULL,
        file_path NVARCHAR(1000) NOT NULL,
        document_id UNIQUEIDENTIFIER NOT NULL,
        uploaded_by UNIQUEIDENTIFIER NOT NULL,
        uploaded_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (document_id) REFERENCES documents(id),
        FOREIGN KEY (uploaded_by) REFERENCES users(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'comments' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE comments (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        text NVARCHAR(MAX) NOT NULL,
        document_id UNIQUEIDENTIFIER NOT NULL,
        author_id UNIQUEIDENTIFIER NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (document_id) REFERENCES documents(id),
        FOREIGN KEY (author_id) REFERENCES users(id)
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'notifications' AND schema_id = SCHEMA_ID('dbo'))
BEGIN
    CREATE TABLE notifications (
        id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
        message NVARCHAR(MAX) NOT NULL,
        is_read BIT NOT NULL DEFAULT 0,
        user_id UNIQUEIDENTIFIER NOT NULL,
        document_id UNIQUEIDENTIFIER NOT NULL,
        created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
        FOREIGN KEY (user_id) REFERENCES users(id),
        FOREIGN KEY (document_id) REFERENCES documents(id)
    );
END
GO

-- Вставляем начальные данные (если таблицы пустые)
IF NOT EXISTS (SELECT * FROM roles)
BEGIN
    INSERT INTO roles (name) VALUES 
    ('ADMIN'),
    ('MANAGER'),
    ('USER'),
    ('VIEWER'),
    ('EDITOR');
END
GO

IF NOT EXISTS (SELECT * FROM statuses)
BEGIN
    INSERT INTO statuses (name, is_final, order_index) VALUES 
    ('Draft', 0, 1),
    ('Under Review', 0, 2),
    ('Approved', 0, 3),
    ('Published', 0, 4),
    ('Archived', 1, 5);
END
GO 