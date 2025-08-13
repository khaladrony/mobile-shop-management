-- Date: 16-july-2025
-- InventoryMovement
ALTER TABLE inventory_movement ADD CONSTRAINT uq_transaction_id UNIQUE (transaction_id);

-- InventoryTransaction
ALTER TABLE `inventory_transaction` ADD UNIQUE(`transaction_id`);