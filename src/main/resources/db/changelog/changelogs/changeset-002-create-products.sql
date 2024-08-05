--liquibase formatted sql

--changeset Stefan:createProductsTable runAlways:true
--comment: Creating Products table
--preConditions onFail:MARK_RAN

--sql
CREATE TABLE IF NOT EXISTS public.Products (
id UUID PRIMARY key default uuid_generate_v4() ,
name VARCHAR(255),
description TEXT,
price DECIMAL,
quantity INTEGER,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP
);
