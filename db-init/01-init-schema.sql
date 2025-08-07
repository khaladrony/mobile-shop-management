-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 07, 2025 at 06:46 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `erpdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `acc_chart_of_accounts`
--

CREATE TABLE `acc_chart_of_accounts` (
  `id` bigint(20) NOT NULL,
  `accounts_code` varchar(20) NOT NULL,
  `accounts_name` varchar(50) NOT NULL,
  `accounts_source` varchar(255) NOT NULL,
  `accounts_type` varchar(255) NOT NULL,
  `accounts_usage` varchar(255) NOT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `group1` varchar(255) DEFAULT NULL,
  `group2` varchar(255) DEFAULT NULL,
  `group3` varchar(255) DEFAULT NULL,
  `group4` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_leaf` bit(1) DEFAULT NULL,
  `master_type` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `acc_default_setup`
--

CREATE TABLE `acc_default_setup` (
  `id` bigint(20) NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  `voucher_print_view` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal_details`
--

CREATE TABLE `acc_journal_details` (
  `id` bigint(20) NOT NULL,
  `amount` double DEFAULT NULL,
  `base_amount` double DEFAULT NULL,
  `chart_of_accounts_id` bigint(20) DEFAULT NULL,
  `chart_of_accounts_source` varchar(255) DEFAULT NULL,
  `chart_of_accounts_type` varchar(255) DEFAULT NULL,
  `chart_of_accounts_usage` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `currency_rate` double DEFAULT NULL,
  `currency_type` varchar(255) DEFAULT NULL,
  `debit_credit_flag` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `particulars` varchar(255) DEFAULT NULL,
  `prime_amount` double DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  `sub_accounts_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `journal_master_id` bigint(20) DEFAULT NULL,
  `chart_of_accounts_code_name` varchar(255) DEFAULT NULL,
  `sub_accounts_code_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `acc_journal_master`
--

CREATE TABLE `acc_journal_master` (
  `id` bigint(20) NOT NULL,
  `action` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `cheque_date` datetime DEFAULT NULL,
  `cheque_no` varchar(255) DEFAULT NULL,
  `cheque_status` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `particulars` varchar(255) DEFAULT NULL,
  `payment_type` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `voucher_date` datetime DEFAULT NULL,
  `voucher_no` varchar(255) DEFAULT NULL,
  `voucher_prefix` varchar(255) DEFAULT NULL,
  `voucher_type` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `bank_account_id` bigint(20) DEFAULT NULL,
  `posting_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `acc_sub_accounts`
--

CREATE TABLE `acc_sub_accounts` (
  `id` bigint(20) NOT NULL,
  `chart_of_accounts_id` bigint(20) NOT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `sub_accounts_code` varchar(20) NOT NULL,
  `sub_accounts_name` varchar(50) NOT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `chart_of_accounts_code` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `app_codes`
--

CREATE TABLE `app_codes` (
  `id` bigint(20) NOT NULL,
  `create_on` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `xcode` varchar(255) DEFAULT NULL,
  `xtype` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `app_settings`
--

CREATE TABLE `app_settings` (
  `id` int(11) NOT NULL,
  `pass_exp` int(11) NOT NULL COMMENT 'in days',
  `pass_exp_alert` int(11) NOT NULL COMMENT 'in days',
  `sess_timeout` int(11) NOT NULL COMMENT 'in minutes',
  `default_pkey` varchar(250) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `updated_by` int(11) NOT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bank_info`
--

CREATE TABLE `bank_info` (
  `id` bigint(20) NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  `bank_account_code` varchar(255) DEFAULT NULL,
  `bank_account_name` varchar(255) DEFAULT NULL,
  `bank_account_no` varchar(255) DEFAULT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `branch_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `customer_info`
--

CREATE TABLE `customer_info` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `chart_of_accounts_id` bigint(20) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee_info`
--

CREATE TABLE `employee_info` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `chart_of_accounts_id` bigint(20) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `employee_code` varchar(255) DEFAULT NULL,
  `employee_name` varchar(255) DEFAULT NULL,
  `employee_pin` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `feature`
--

CREATE TABLE `feature` (
  `feature_id` bigint(20) NOT NULL,
  `feature_name` varchar(200) NOT NULL,
  `feature_code` varchar(20) NOT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `module` varchar(1000) DEFAULT NULL,
  `controller` varchar(100) DEFAULT NULL,
  `action` varchar(100) DEFAULT NULL,
  `component` varchar(100) DEFAULT NULL,
  `is_menu` tinyint(1) NOT NULL DEFAULT 1,
  `parent_id` bigint(20) DEFAULT NULL,
  `type` enum('Module','Feature Group','Feature') NOT NULL,
  `need_permission` tinyint(1) NOT NULL DEFAULT 1,
  `sort_order` int(11) NOT NULL DEFAULT 7,
  `created_by` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `version_no` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_movement`
--

CREATE TABLE `inventory_movement` (
  `id` bigint(20) NOT NULL,
  `create_on` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `action` enum('ISSUE','RECEIPT','TRANSFER') DEFAULT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `sign` int(11) DEFAULT NULL,
  `status` enum('APPROVED','DELIVERED','ON_TRANSFER','OPEN','PARTLY_DELIVERED') DEFAULT NULL,
  `supplier_code` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `warehouse` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `from_warehouse` varchar(255) DEFAULT NULL,
  `to_warehouse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_movement_item`
--

CREATE TABLE `inventory_movement_item` (
  `id` bigint(20) NOT NULL,
  `create_on` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `inventory_movement_id` bigint(20) DEFAULT NULL,
  `inventory_transaction_id` bigint(20) DEFAULT NULL,
  `item_code` varchar(255) DEFAULT NULL,
  `line_number` int(11) DEFAULT NULL,
  `quantity` decimal(38,2) DEFAULT NULL,
  `quantity_confirm` decimal(38,2) DEFAULT NULL,
  `rate` decimal(38,2) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `value` decimal(38,2) DEFAULT NULL,
  `document_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_transaction`
--

CREATE TABLE `inventory_transaction` (
  `id` bigint(20) NOT NULL,
  `create_on` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `action` enum('ISSUE','RECEIPT') DEFAULT NULL,
  `customer_code` varchar(255) DEFAULT NULL,
  `document_id` bigint(20) DEFAULT NULL,
  `document_row` int(11) DEFAULT NULL,
  `document_transaction_id` varchar(255) DEFAULT NULL,
  `document_type` varchar(255) DEFAULT NULL,
  `item_code` varchar(255) DEFAULT NULL,
  `line_number` int(11) DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `quantity` decimal(38,2) DEFAULT NULL,
  `sign` int(11) DEFAULT NULL,
  `supplier_code` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `transaction_id` varchar(255) NOT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `value` decimal(38,2) DEFAULT NULL,
  `warehouse` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `document_detail_id` bigint(20) DEFAULT NULL,
  `document_no` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `item_master`
--

CREATE TABLE `item_master` (
  `id` bigint(20) NOT NULL,
  `create_on` datetime(6) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime(6) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `imei` varchar(255) DEFAULT NULL,
  `item_code` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `standard_cost` decimal(38,2) DEFAULT NULL,
  `standard_price` decimal(38,2) DEFAULT NULL,
  `storage` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `organization`
--

CREATE TABLE `organization` (
  `id` bigint(20) NOT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_phone` varchar(255) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `web_url` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `role_feature`
--

CREATE TABLE `role_feature` (
  `role_feature_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `feature_id` bigint(20) NOT NULL,
  `is_home` tinyint(1) NOT NULL DEFAULT 0,
  `created_by` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` bit(1) NOT NULL DEFAULT b'1',
  `version_no` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier_info`
--

CREATE TABLE `supplier_info` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `chart_of_accounts_id` bigint(20) DEFAULT NULL,
  `create_on` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `supplier_code` varchar(255) DEFAULT NULL,
  `supplier_name` varchar(255) DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `system_role`
--

CREATE TABLE `system_role` (
  `role_id` bigint(20) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `role_code` varchar(20) NOT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `version_no` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE `user_info` (
  `user_id` bigint(20) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `user_code` varchar(20) DEFAULT NULL,
  `usremail` varchar(150) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `usrpkey` varchar(500) NOT NULL,
  `pkey_last_change` datetime DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `country_id` int(11) DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `is_master` tinyint(1) NOT NULL DEFAULT 0,
  `lan_id` varchar(100) NOT NULL,
  `password_updated_by` bigint(20) DEFAULT NULL,
  `password_updated_on` timestamp NULL DEFAULT NULL,
  `is_default_password_change` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_logins`
--

CREATE TABLE `user_logins` (
  `login_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `used_email` varchar(100) NOT NULL,
  `used_pwd` varchar(200) NOT NULL,
  `session_start` datetime NOT NULL,
  `session_end` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `log_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `agent` varchar(255) NOT NULL,
  `terminal` varchar(30) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `lan_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `updated_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `version_no` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `acc_chart_of_accounts`
--
ALTER TABLE `acc_chart_of_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_default_setup`
--
ALTER TABLE `acc_default_setup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `acc_journal_details`
--
ALTER TABLE `acc_journal_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdwb07tgbbx1iglgspo9iol6iw` (`journal_master_id`);

--
-- Indexes for table `acc_journal_master`
--
ALTER TABLE `acc_journal_master`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9d04kgj84kq5yk7d056h4qm6c` (`voucher_no`);

--
-- Indexes for table `acc_sub_accounts`
--
ALTER TABLE `acc_sub_accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `app_codes`
--
ALTER TABLE `app_codes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bank_info`
--
ALTER TABLE `bank_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_a3hl140o74y9rhtne6eg0h5er` (`bank_account_code`);

--
-- Indexes for table `customer_info`
--
ALTER TABLE `customer_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4f8thns76i69ghc7j7x7twiof` (`customer_code`);

--
-- Indexes for table `employee_info`
--
ALTER TABLE `employee_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_lu91gytiwxamsdgasr94pmx08` (`employee_code`),
  ADD UNIQUE KEY `UK_7gmv7034i4xdeh4cvjunws1fm` (`employee_pin`);

--
-- Indexes for table `feature`
--
ALTER TABLE `feature`
  ADD PRIMARY KEY (`feature_id`),
  ADD KEY `version_no` (`version_no`);

--
-- Indexes for table `inventory_movement`
--
ALTER TABLE `inventory_movement`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_transaction_id` (`transaction_id`),
  ADD UNIQUE KEY `UK7il6u2w5edqhtnksdijbcvp7a` (`transaction_id`);

--
-- Indexes for table `inventory_movement_item`
--
ALTER TABLE `inventory_movement_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt8o0qw3i048shjkpbppgj5jhj` (`inventory_movement_id`);

--
-- Indexes for table `inventory_transaction`
--
ALTER TABLE `inventory_transaction`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `transaction_id` (`transaction_id`);

--
-- Indexes for table `item_master`
--
ALTER TABLE `item_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `organization`
--
ALTER TABLE `organization`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `role_feature`
--
ALTER TABLE `role_feature`
  ADD PRIMARY KEY (`role_feature_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `version_no` (`version_no`);

--
-- Indexes for table `supplier_info`
--
ALTER TABLE `supplier_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6d1n9vg5lnn15vyv5m105cyag` (`supplier_code`);

--
-- Indexes for table `system_role`
--
ALTER TABLE `system_role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_name` (`role_name`),
  ADD KEY `version_no` (`version_no`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_info_email_key` (`usremail`),
  ADD UNIQUE KEY `user_info_lan_id_uindex` (`lan_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user_logins`
--
ALTER TABLE `user_logins`
  ADD PRIMARY KEY (`login_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_role_id`),
  ADD UNIQUE KEY `user_role_uq` (`user_id`,`role_id`,`active`) USING BTREE,
  ADD KEY `user_id` (`user_id`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `acc_chart_of_accounts`
--
ALTER TABLE `acc_chart_of_accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_default_setup`
--
ALTER TABLE `acc_default_setup`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_journal_details`
--
ALTER TABLE `acc_journal_details`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_journal_master`
--
ALTER TABLE `acc_journal_master`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `acc_sub_accounts`
--
ALTER TABLE `acc_sub_accounts`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `app_codes`
--
ALTER TABLE `app_codes`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bank_info`
--
ALTER TABLE `bank_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `customer_info`
--
ALTER TABLE `customer_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee_info`
--
ALTER TABLE `employee_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `feature`
--
ALTER TABLE `feature`
  MODIFY `feature_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inventory_movement`
--
ALTER TABLE `inventory_movement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inventory_movement_item`
--
ALTER TABLE `inventory_movement_item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `inventory_transaction`
--
ALTER TABLE `inventory_transaction`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `item_master`
--
ALTER TABLE `item_master`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `organization`
--
ALTER TABLE `organization`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `role_feature`
--
ALTER TABLE `role_feature`
  MODIFY `role_feature_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `supplier_info`
--
ALTER TABLE `supplier_info`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `system_role`
--
ALTER TABLE `system_role`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_info`
--
ALTER TABLE `user_info`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_logins`
--
ALTER TABLE `user_logins`
  MODIFY `login_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `acc_journal_details`
--
ALTER TABLE `acc_journal_details`
  ADD CONSTRAINT `FKdwb07tgbbx1iglgspo9iol6iw` FOREIGN KEY (`journal_master_id`) REFERENCES `acc_journal_master` (`id`);

--
-- Constraints for table `inventory_movement_item`
--
ALTER TABLE `inventory_movement_item`
  ADD CONSTRAINT `FKt8o0qw3i048shjkpbppgj5jhj` FOREIGN KEY (`inventory_movement_id`) REFERENCES `inventory_movement` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
