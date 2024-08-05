--liquibase formatted sql

--changeset Stefan:createOrdersTable runAlways:true
--comment: Creating Orders table
--preConditions onFail:MARK_RAN

--sql
CREATE TABLE IF NOT EXISTS public.Orders (
id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
user_id UUID,
product_id UUID,
quantity INTEGER,
order_date TIMESTAMP,
status VARCHAR,
FOREIGN KEY (user_id) REFERENCES public.users(id),
FOREIGN KEY (product_id) REFERENCES public.products(id)
);