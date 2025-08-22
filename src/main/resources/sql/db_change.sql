-- Date: 16-july-2025
-- InventoryMovement
ALTER TABLE inventory_movement ADD CONSTRAINT uq_transaction_id UNIQUE (transaction_id);

-- InventoryTransaction
ALTER TABLE `inventory_transaction` ADD UNIQUE(`transaction_id`);

-- user_info
ALTER TABLE `user_info` ADD `organization_id` BIGINT(20) NOT NULL AFTER `user_id`;

-- user_logins
ALTER TABLE `user_logins` ADD `organization_id` BIGINT(20) NULL AFTER `user_id`;

-- POS table Order
ALTER TABLE `orders` CHANGE `status` `status` ENUM('CANCELLED','COMPLETED','HELD','PENDING','RETURNED') CHARACTER SET big5 COLLATE big5_chinese_ci NULL DEFAULT NULL;