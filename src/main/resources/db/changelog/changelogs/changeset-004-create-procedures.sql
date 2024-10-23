--liquibase formatted sql

--changeset Stefan:createProcedures runAlways:true splitStatements:false
--comment: Creating procedures insert_user and place_order
--preConditions onFail:MARK_RAN

--sql
CREATE
OR REPLACE PROCEDURE insert_user(
  IN p_username VARCHAR(255),
  IN p_password VARCHAR(255),
  IN p_email VARCHAR(255),
  OUT p_id UUID,
  OUT p_created_at TIMESTAMP,
  OUT p_updated_at TIMESTAMP
) LANGUAGE plpgsql AS
$$ BEGIN INSERT INTO Users (username, password, email)
VALUES
  (p_username, p_password, p_email) RETURNING id,
  created_at,
  updated_at INTO p_id,
  p_created_at,
  p_updated_at;
END;
$$;

CREATE OR REPLACE PROCEDURE place_order(
IN p_user_id uuid,
IN p_product_id uuid,
IN p_quantity int,
OUT p_order_id uuid,
OUT p_order_date timestamp,
OUT p_status varchar
)
LANGUAGE plpgsql
AS $$
BEGIN
-- Check if the product exists
IF NOT EXISTS (SELECT 1 FROM products WHERE id = p_product_id) THEN
RAISE EXCEPTION 'Product not found';
END IF;

-- Check if the product quantity is sufficient
IF (SELECT quantity FROM products WHERE id = p_product_id) < p_quantity THEN
RAISE EXCEPTION 'Insufficient product quantity';
END IF;

-- Check if the user exists
IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
RAISE EXCEPTION 'User not found';
END IF;

-- Insert a new order into the orders table
INSERT INTO orders (user_id, product_id, quantity, order_date, status)
VALUES (p_user_id, p_product_id, p_quantity, NOW(), 'Pending')
RETURNING id, order_date, status INTO p_order_id, p_order_date, p_status;

-- Update the product quantity in the products table
UPDATE products
SET quantity = quantity - p_quantity, updated_at = NOW()
WHERE id = p_product_id;
END;
$$;