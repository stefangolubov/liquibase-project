--liquibase formatted sql

--changeset Stefan:createUsersTable runAlways:true
--comment: Creating Users table
--preConditions onFail:MARK_RAN

--sql
CREATE TABLE IF NOT EXISTS Users (
id UUID PRIMARY key default uuid_generate_v4() ,
username VARCHAR(255) UNIQUE,
password VARCHAR(255),
email VARCHAR(255) UNIQUE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP
);