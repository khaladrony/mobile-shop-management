-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Aug 07, 2025 at 06:50 AM
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

--
-- Dumping data for table `acc_chart_of_accounts`
--

INSERT INTO `acc_chart_of_accounts` (`id`, `accounts_code`, `accounts_name`, `accounts_source`, `accounts_type`, `accounts_usage`, `create_on`, `created_by`, `group1`, `group2`, `group3`, `group4`, `is_active`, `is_leaf`, `master_type`, `organization_id`, `updated_by`, `updated_on`) VALUES
(7, '100009', 'MICROSOFT LICENSE-09478', 'None', 'Asset', 'Ledger', '2021-05-01 15:47:49', 2, 'Non-Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:52:50'),
(6, '100007', 'EXTERNAL PURCHASED ERP', 'None', 'Asset', 'Ledger', '2021-05-01 15:44:17', 2, 'Non-Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-04 22:25:10'),
(8, '100010', 'OTHER ADVANCES', 'Subaccount', 'Asset', 'Ledger', '2021-05-01 15:48:08', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:53:59'),
(9, '100011', 'PREPAID RENT', 'Subaccount', 'Asset', 'Ledger', '2021-05-01 15:48:25', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:55:16'),
(11, '100013', 'Cash in Hand', 'None', 'Asset', 'Cash', '2021-05-10 15:23:54', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-04 22:26:53'),
(12, '100014', 'Cash at Bank', 'None', 'Asset', 'Bank', '2021-05-10 15:29:16', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:56:02'),
(13, '100015', 'OTHER ADVANCES', 'None', 'Asset', 'Ledger', '2021-05-10 15:31:39', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:56:34'),
(14, '100016', 'ELECTRICAL FITTING & EQUIPMENT', 'None', 'Asset', 'Ledger', '2021-05-10 15:50:18', 2, 'Non-Current Assets', NULL, NULL, NULL, b'1', b'1', 'Balance Sheet', 1, 2, '2025-08-05 23:57:23'),
(15, '100018', 'SHORT TERM INVESTMENT IN SECURITIES', 'None', 'Asset', 'Ledger', '2021-05-17 05:55:03', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-05 23:57:50'),
(16, '500001', 'BASIC SALARY', 'None', 'Expenditure', 'Ledger', '2021-05-21 16:45:02', 2, 'Operating Expense', NULL, NULL, NULL, b'1', b'0', 'Revenue', 1, 2, '2025-08-06 00:12:30'),
(17, '500002', 'HOUSE RENT', 'None', 'Expenditure', 'Ledger', '2021-05-21 16:45:24', 2, 'Operating Expense', NULL, NULL, NULL, b'1', b'0', 'Revenue', 1, 2, '2025-08-06 00:12:57'),
(18, '500003', 'MEDICAL ALLOWANCE', 'None', 'Expenditure', 'Ledger', '2021-05-21 16:45:45', 2, 'Operating Expense', NULL, NULL, NULL, b'1', b'0', 'Revenue', 1, 2, '2025-08-06 00:13:25'),
(19, '400001', 'REVENUE LICENSING FEES', 'None', 'Income', 'Ledger', '2021-05-23 04:07:11', 2, 'Operating Income', NULL, NULL, NULL, b'1', b'0', 'Revenue', 1, 2, '2025-08-06 00:10:13'),
(20, '200001', 'PAYABLE TO VENDORS', 'Supplier', 'Liability', 'AP', '2021-06-02 17:53:30', 2, 'Current Liability', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-06 00:07:31'),
(21, '100019', 'TRADE RECEIVABLES', 'Customer', 'Asset', 'AR', '2021-06-02 17:56:31', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-05 23:58:20'),
(22, '100020', 'ADVANCE TO EMPLOYEE AGAINST IOU', 'Employee', 'Asset', 'Ledger', '2021-06-02 17:57:01', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-05 23:58:45'),
(23, '100021', 'ADVANCE TO EMPLOYEE AGAINST SALARY', 'Employee', 'Asset', 'Ledger', '2021-06-02 17:57:15', 2, 'Current Assets', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-05 23:59:07'),
(27, '300001', 'Retained Earning', 'None', 'Equity', 'Ledger', '2025-08-04 20:20:44', NULL, 'Shareholders Equity', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 2, '2025-08-06 00:09:10'),
(28, '200002', 'SECURITY DEPOSITS RECEIVED', 'None', 'Liability', 'Ledger', '2025-08-06 00:17:46', 2, 'Non-current Liability', NULL, NULL, NULL, b'1', b'0', 'Balance Sheet', 1, 0, NULL);

--
-- Dumping data for table `acc_default_setup`
--

INSERT INTO `acc_default_setup` (`id`, `organization_id`, `voucher_print_view`) VALUES
(1, 1, 'A5');

--
-- Dumping data for table `acc_sub_accounts`
--

INSERT INTO `acc_sub_accounts` (`id`, `chart_of_accounts_id`, `create_on`, `created_by`, `organization_id`, `sub_accounts_code`, `sub_accounts_name`, `updated_by`, `updated_on`, `status`, `chart_of_accounts_code`) VALUES
(1, 8, '2021-05-18 07:41:57', 2, 1, '10001001', 'SHORT TERM INVESTMENT IN SECURITIES', 2, '2021-05-18 12:16:55', b'1', NULL),
(2, 8, '2021-05-18 07:45:13', 2, 1, '10001002', 'Weeeee subaccount2', NULL, NULL, b'1', NULL),
(3, 8, '2021-05-18 09:28:35', 2, 1, '10001003', 'Weeeee subaccount3', NULL, NULL, b'1', NULL),
(4, 8, '2021-05-18 09:43:51', 2, 1, '10001004', 'Weeeee subaccount4', NULL, NULL, b'1', NULL),
(5, 8, '2021-05-18 09:46:13', 2, 1, '10001005', 'Weeeee subaccount5', 2, '2021-05-18 10:26:20', b'1', NULL),
(6, 8, '2021-05-18 09:47:00', 2, 1, '10001006', 'Weeeee subaccount6', 2, '2021-05-18 10:55:26', b'1', NULL),
(7, 8, '2021-05-18 09:50:36', 2, 1, '10001007', 'Weeeee subaccount7', 2, '2021-05-18 10:55:45', b'1', NULL),
(8, 8, '2021-05-18 09:50:47', 2, 1, '10001008', 'Weeeee subaccount8', 2, '2021-05-18 10:56:30', b'1', NULL),
(10, 9, '2021-05-18 12:25:37', 2, 1, '10001101', 'PREPAID RENT 01', NULL, NULL, b'1', NULL),
(11, 9, '2021-05-18 12:25:46', 2, 1, '10001102', 'PREPAID RENT 02', NULL, NULL, b'1', NULL);

--
-- Dumping data for table `app_codes`
--

INSERT INTO `app_codes` (`id`, `create_on`, `created_by`, `organization_id`, `updated_by`, `updated_on`, `active`, `description`, `xcode`, `xtype`) VALUES
(1, NULL, NULL, 1, NULL, NULL, b'1', '', 'Samsung', 'Brand'),
(2, NULL, NULL, 1, NULL, NULL, b'1', '', 'Sony', 'Brand'),
(3, NULL, NULL, 1, NULL, NULL, b'1', '', 'Nokia', 'Brand'),
(4, NULL, NULL, 1, NULL, NULL, b'1', '', 'Apple', 'Brand'),
(5, NULL, NULL, 1, NULL, NULL, b'1', '', 'White', 'Color'),
(6, NULL, NULL, 1, NULL, NULL, b'1', '', 'Black', 'Color'),
(7, NULL, NULL, 1, NULL, NULL, b'1', '', 'Mobile', 'Category'),
(8, NULL, 2, 1, NULL, NULL, b'1', '', 'Pcs', 'Unit'),
(9, NULL, 2, 1, NULL, NULL, b'1', '', 'Walton', 'Brand'),
(10, NULL, 2, 1, NULL, NULL, b'1', '', 'Huawei', 'Brand'),
(11, NULL, 2, 1, NULL, NULL, b'1', '', 'OPEN', 'Inventory Status'),
(12, NULL, 2, 1, NULL, NULL, b'1', '', 'APPROVED', 'Inventory Status'),
(13, NULL, 2, 1, NULL, NULL, b'1', '', 'ON_TRANSFER', 'Inventory Status'),
(14, NULL, 2, 1, NULL, NULL, b'1', '', 'DELIVERED', 'Inventory Status'),
(15, NULL, 2, 1, NULL, NULL, b'1', '', 'PARTLY_DELIVERED', 'Inventory Status'),
(16, NULL, 2, 1, NULL, NULL, b'1', '', 'Central Warehouse', 'Warehouse'),
(17, NULL, NULL, 1, NULL, NULL, b'1', '', 'Dhaka Central Warehouse', 'Warehouse'),
(18, NULL, NULL, 1, NULL, NULL, b'1', '', 'Chattogram Central Warehouse', 'Warehouse'),
(19, NULL, 2, 1, NULL, NULL, b'1', '', 'RECEIPT', 'Inventory Action'),
(20, NULL, 2, 1, NULL, NULL, b'1', '', 'ISSUE', 'Inventory Action'),
(21, NULL, 2, 1, NULL, NULL, b'1', '', 'TRANSFER', 'Inventory Action'),
(22, '2025-07-30 20:31:41.000000', 2, 1, 2, '2025-07-30 20:31:41.000000', b'1', '', 'Summary', 'Report Type'),
(23, '2025-07-30 20:32:06.000000', 2, 1, 2, '2025-07-30 20:32:06.000000', b'1', '', 'Details', 'Report Type'),
(24, '2025-07-31 14:11:57.000000', 2, 1, 2, '2025-07-31 14:11:57.000000', b'1', '', 'DRAFT', 'Voucher Status'),
(25, '2025-07-31 14:12:21.000000', 2, 1, 2, '2025-07-31 14:12:21.000000', b'1', '', 'POSTED', 'Voucher Status'),
(26, '2025-08-03 10:54:51.000000', 2, 1, 2, '2025-08-03 10:54:51.000000', b'1', '', 'BRAC Bank PLC', 'Bank Name'),
(27, '2025-08-03 10:55:16.000000', 2, 1, 2, '2025-08-03 10:55:16.000000', b'1', '', 'City Bank PLC', 'Bank Name'),
(28, '2025-08-03 10:55:46.000000', 2, 1, 2, '2025-08-03 10:55:46.000000', b'1', '', 'Dutch-Bangla Bank PLC', 'Bank Name'),
(29, '2025-08-03 10:56:21.000000', 2, 1, 2, '2025-08-03 10:56:21.000000', b'1', '', 'Islami Bank Bangladesh PLC', 'Bank Name'),
(30, '2025-08-03 10:58:20.000000', 2, 1, 2, '2025-08-03 10:58:20.000000', b'1', '', 'Standard Chartered Bank', 'Bank Name'),
(31, '2025-08-04 20:36:17.000000', 2, 1, 2, '2025-08-04 20:36:17.000000', b'1', '', 'Current Assets', 'Asset'),
(32, '2025-08-04 20:36:41.000000', 2, 1, 2, '2025-08-04 20:36:41.000000', b'1', '', 'Non-Current Assets', 'Asset'),
(33, '2025-08-06 00:00:26.000000', 2, 1, 2, '2025-08-06 00:00:26.000000', b'1', '', 'Current Liability', 'Liability'),
(34, '2025-08-06 00:03:25.000000', 2, 1, 2, '2025-08-06 00:07:07.000000', b'1', '', 'Non-current Liability', 'Liability'),
(36, '2025-08-06 00:08:50.000000', 2, 1, 2, '2025-08-06 00:08:50.000000', b'1', '', 'Shareholders Equity', 'Equity'),
(37, '2025-08-06 00:10:05.000000', 2, 1, 2, '2025-08-06 00:10:05.000000', b'1', '', 'Operating Income', 'Income'),
(38, '2025-08-06 00:10:59.000000', 2, 1, 2, '2025-08-06 00:10:59.000000', b'1', '', 'Operating Expense', 'Expenditure');

--
-- Dumping data for table `app_settings`
--

INSERT INTO `app_settings` (`id`, `pass_exp`, `pass_exp_alert`, `sess_timeout`, `default_pkey`, `active`, `updated_by`, `updated_on`) VALUES
(1, 90, 3, 60, 'abcd1234', 1, 2, '2021-04-27 23:25:10');

--
-- Dumping data for table `bank_info`
--

INSERT INTO `bank_info` (`id`, `organization_id`, `bank_account_code`, `bank_account_name`, `bank_account_no`, `bank_name`, `branch_name`, `address`, `create_on`, `created_by`, `phone`, `status`, `updated_by`, `updated_on`) VALUES
(1, 1, 'BA-0001', 'khalad', 'BBL-00023232323', 'BRAC Bank PLC', '', '', '2021-06-01 11:46:13', 2, '', b'1', 2, '2025-08-03 10:58:02'),
(2, 1, 'BA-0002', 'Mosharaf', 'SCB9000000000001', 'Standard Chartered Bank', '', 'Rampura.', '2021-06-01 12:01:40', 2, '', b'1', 2, '2025-08-03 10:58:34'),
(3, 1, 'BA-0003', 'Mosharaf', 'BBL-00023230000001', 'BRAC Bank PLC', 'gulshan', 'Gulshan', '2021-06-01 17:29:58', 2, '20934343', b'1', 2, '2025-08-03 10:56:57'),
(4, 1, 'BA-0004', 'Rahat fate ali', 'BBL-00023233400', 'BRAC Bank PLC', 'gulshan', 'Banasree', '2021-06-02 11:12:18', 2, '01730796779', b'1', 2, '2025-08-03 10:56:49');

--
-- Dumping data for table `customer_info`
--

INSERT INTO `customer_info` (`id`, `address`, `chart_of_accounts_id`, `create_on`, `created_by`, `customer_code`, `customer_name`, `email`, `organization_id`, `phone`, `status`, `updated_by`, `updated_on`) VALUES
(1, 'rampura', 0, '2021-06-02 14:56:38', 2, 'CUS-0001', 'Mosharaf', 'rony@gmail.com', 1, '01730796779', b'1', 2, '2021-06-02 14:57:06');

--
-- Dumping data for table `employee_info`
--

INSERT INTO `employee_info` (`id`, `address`, `chart_of_accounts_id`, `create_on`, `created_by`, `department`, `designation`, `email`, `employee_code`, `employee_name`, `employee_pin`, `organization_id`, `phone`, `salary`, `status`, `updated_by`, `updated_on`) VALUES
(1, 'banasree', 0, '2021-06-02 15:27:45', 2, NULL, 'Eng', 'mdkhaled.mosharraf@bracits.com', 'EMP-0001', 'mosharaf', NULL, 1, '01730796779', 0, b'1', 2, '2021-06-02 15:27:58'),
(2, '', 0, '2021-06-10 04:07:34', 2, NULL, '', '', 'EMP-0002', 'Md. Jaman', NULL, 1, '', 0, b'1', NULL, NULL),
(3, '', 0, '2021-06-10 12:38:00', 2, NULL, '', '', 'EMP-0003', 'Md. Rafiq', NULL, 1, '', 0, b'1', NULL, NULL),
(4, '', 0, '2021-06-10 12:38:13', 2, NULL, '', '', 'EMP-0004', 'Md. Karim', NULL, 1, '', 0, b'1', NULL, NULL),
(5, '', 0, '2021-06-18 16:35:19', 2, NULL, 'Banker', 'mahabub@gmail.com', 'EMP-0005', 'Md. Mahabub', '2123', 1, '', 0, b'1', 2, '2021-06-18 18:01:14');

--
-- Dumping data for table `feature`
--

INSERT INTO `feature` (`feature_id`, `feature_name`, `feature_code`, `url`, `note`, `module`, `controller`, `action`, `component`, `is_menu`, `parent_id`, `type`, `need_permission`, `sort_order`, `created_by`, `created_on`, `updated_by`, `updated_on`, `active`, `version_no`) VALUES
(1, 'Auth', '', NULL, NULL, '', NULL, NULL, NULL, 1, NULL, 'Module', 1, 1, 1, '2019-04-20 21:08:57', 111, '2018-09-17 16:08:33', 1, 1),
(2, 'Feature', '', NULL, NULL, '', NULL, NULL, NULL, 1, 1, 'Feature Group', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2019-06-02 04:34:30', 1, 1),
(3, 'Role', '', NULL, NULL, '', NULL, NULL, NULL, 1, 1, 'Feature Group', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2019-06-02 04:34:30', 1, 1),
(4, 'New Feature', '', '/user_auth/feature/view,/user_auth/feature/save', NULL, 'user_auth', 'feature', 'view', 'feature_add_view', 1, 2, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', 111, '2021-05-05 01:53:59', 1, 1),
(5, 'Feature List', '', '/user_auth/feature/view,/user_auth/feature/filter', NULL, 'user_auth', 'feature', 'view', 'feature_list_view', 1, 2, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', 111, '2021-05-05 01:53:59', 1, 1),
(6, 'Show Feature', '', '/user_auth/feature/view,/user_auth/feature/get/{id}', NULL, 'user_auth', 'feature', '', 'feature_show', 0, 2, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(7, 'Edit Feature', '', '/user_auth/feature/view,/user_auth/feature/get/{id},/user_auth/feature/update/{id}', NULL, 'user_auth', 'feature', '', 'feature_update', 0, 2, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(8, 'Active/Inactive', '', '/user_auth/feature/active/{id}', NULL, 'user_auth', 'feature', '', 'feature_active_inactive', 0, 2, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(9, 'New Role', '', '/user_auth/role/view,/user_auth/role/save', NULL, 'user_auth', 'role', 'view', 'role_add_view', 1, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(10, 'Role List', '', '/user_auth/role/view,/user_auth/role/filter', NULL, 'user_auth', 'role', 'view', 'role_list_view', 1, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(11, 'Show Role', '', '/user_auth/role/view,/user_auth/role/get/{id}', NULL, 'user_auth', 'role', '', 'role_show', 0, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(12, 'Edit Role', '', '/user_auth/role/view,/user_auth/role/get/{id},/user_auth/role/update/{id}', NULL, 'user_auth', 'role', '', 'role_update', 0, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(13, 'Active/Inactive Role', '', '/user_auth/role/active/{id}', NULL, 'user_auth', 'role', '', 'role_active_inactive', 0, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(14, 'Role Feature', '', '', NULL, 'user_auth', 'role', 'view', 'role_feature_view', 1, 3, 'Feature', 1, 7, 1, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(15, 'User', '', '', '', '', NULL, '', '', 1, 1, 'Feature Group', 1, 7, 111, '2019-04-25 07:50:43', NULL, '2019-06-02 04:34:30', 1, 1),
(16, 'User List', '', '/user_auth/user/view,/user_auth/user/filter', '', 'user_auth', 'user', 'view', 'user_list_view', 1, 15, 'Feature', 1, 7, 111, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(19, 'Manage User', '', '/user_auth/user/view,/user_auth/user/get/{id},/user_auth/user/update/{id}', '', 'user_auth', 'user', 'view', 'user_edit_view', 0, 15, 'Feature', 1, 7, 111, '2019-04-25 07:50:43', 111, '2021-05-05 01:53:59', 1, 1),
(20, 'New user', '', '/user_auth/user/view,,/user_auth/user/save', '', 'user_auth', 'user', 'view', 'user_add_view', 1, 15, 'Feature', 1, 7, 0, '2019-04-25 07:50:43', NULL, '2021-05-05 01:53:59', 1, 1),
(21, 'Dashboard', '', '', '', '', NULL, '', '', 0, NULL, 'Module', 1, 0, 1, '2019-07-02 10:10:27', NULL, '2025-06-24 12:52:58', 1, 1562041089704),
(22, 'ViewType', '', '', '', '', NULL, '', '', 0, 21, 'Feature Group', 1, 7, 1, '2019-07-02 10:13:43', 1, '2019-07-01 22:20:50', 1, 1562041712709),
(23, 'StaticView', '', '/dashboard/', '', 'dashboard', 'statics', 'view', 'dashboard_static_view', 0, 22, 'Feature', 1, 7, 1, '2019-07-02 10:23:01', NULL, '2025-06-24 13:02:32', 1, 1562041842977),
(24, 'Accounts', '', '', 'Accounts', '', NULL, '', '', 1, NULL, 'Module', 1, 9, 1, '2021-04-25 21:10:19', NULL, '2025-06-24 12:52:51', 1, 1619363419435),
(25, 'Debit Voucher', '', '', 'Debit Voucher', '', NULL, '', '', 1, 24, 'Feature Group', 1, 9, 1, '2021-04-25 21:13:58', NULL, '2021-04-25 09:13:58', 1, 1619363638285),
(26, 'Debit Voucher Create', '', '/accounts/debit_voucher/save', 'Debit Voucher Create', 'accounts', 'debit_voucher', 'view', 'acc_debit_voucher_add_view', 1, 25, 'Feature', 1, 9, 1, '2021-04-25 21:22:32', NULL, '2021-04-25 09:22:32', 1, 1619364152407),
(27, 'Credit Voucher', '', '', 'Credit Voucher', '', NULL, '', '', 1, 24, 'Feature Group', 1, 10, 2, '2021-04-25 21:39:50', NULL, '2021-05-31 12:34:31', 1, 1619365189953),
(28, 'Journal Voucher', '', '', 'Journal Voucher', '', NULL, '', '', 1, 24, 'Feature Group', 1, 11, 2, '2021-04-25 21:40:37', NULL, '2021-05-31 12:34:31', 1, 1619365237917),
(29, 'Credit Voucher Create', '', '/accounts/credit_voucher/save', 'Credit Voucher Create', 'accounts', 'credit_voucher', 'view', 'acc_credit_voucher_add_view', 1, 27, 'Feature', 1, 10, 2, '2021-04-25 21:43:26', NULL, '2021-05-31 12:30:22', 1, 1619365406795),
(30, 'Journal Voucher Create', '', '/accounts/journal_voucher/save', 'Journal Voucher Create', 'accounts', 'journal_voucher', 'view', 'acc_journal_voucher_add_view', 1, 28, 'Feature', 1, 11, 2, '2021-04-25 21:47:16', NULL, '2021-05-31 12:30:41', 1, 1619365636841),
(31, 'Chart Of Accounts', '', '', 'Accounts Setup', '', NULL, '', '', 1, 24, 'Feature Group', 1, 14, 1, '2021-04-27 21:41:17', 2, '2021-06-09 23:34:57', 1, 1623303297065),
(32, 'Chart of Accounts Create', '', '/accounts/chart_of_accounts/save', '', 'accounts', 'chart_of_accounts', 'view', 'acc_chart_of_accounts_add_view', 1, 31, 'Feature', 1, 13, 1, '2021-04-27 21:46:16', 2, '2021-05-31 12:31:12', 1, 1621185710111),
(33, 'Chart of Accounts List', '', '/accounts/chart_of_accounts/get/all', '', 'accounts', 'chart_of_accounts', 'view', 'acc_chart_of_accounts_list_view', 1, 31, 'Feature', 1, 13, 2, '2021-05-16 16:06:25', NULL, '2021-05-31 12:31:12', 1, 1621159584988),
(34, 'Chart of Accounts Update', '', '/accounts/chart_of_accounts/update', '', 'accounts', 'chart_of_accounts', 'view', 'acc_chart_of_accounts_update_view', 0, 31, 'Feature', 1, 13, 2, '2021-05-16 23:24:21', 2, '2021-05-31 12:31:12', 1, 1621186240229),
(35, 'Sub Accounts Create', '', '/accounts/sub_accounts/save', '', 'accounts', 'sub_accounts', 'view', 'acc_sub_accounts_add_view', 1, 31, 'Feature', 1, 13, 2, '2021-05-17 16:03:22', 2, '2021-05-31 12:31:12', 1, 1621312957963),
(36, 'Debit Voucher List', '', 'accounts/debit_voucher/get/voucher_list', 'Debit Voucher Create List', 'accounts', 'debit_voucher', 'view', 'acc_debit_voucher_list_view', 1, 25, 'Feature', 1, 9, 2, '2021-05-25 02:02:53', 2, '2021-05-28 03:43:44', 1, 1622195024742),
(37, 'Debit Voucher Update', '', '/accounts/debit_voucher/update', 'Debit Voucher Update', 'accounts', 'debit_voucher', 'view', 'acc_debit_voucher_update_view', 0, 25, 'Feature', 1, 9, 2, '2021-05-26 04:43:27', NULL, '2021-05-25 16:43:27', 1, 1621982607450),
(38, 'Credit Voucher List', '', '/accounts/credit_voucher/get/voucher_list', 'Credit Voucher List', 'accounts', 'credit_voucher', 'view', 'acc_credit_voucher_list_view', 1, 27, 'Feature', 1, 10, 2, '2021-05-28 15:58:26', NULL, '2021-05-31 12:30:22', 1, 1622195906931),
(39, 'Credit Voucher Update', '', '/accounts/credit_voucher/update', 'Credit Voucher Update', 'accounts', 'credit_voucher', 'view', 'acc_credit_voucher_update_view', 0, 27, 'Feature', 1, 10, 2, '2021-05-28 16:00:09', NULL, '2021-05-31 12:30:22', 1, 1622196009042),
(40, 'Journal Voucher Update', '', '/accounts/journal_voucher/update', 'Journal Voucher Update', 'accounts', 'journal_voucher', 'view', 'acc_journal_voucher_update_view', 0, 28, 'Feature', 1, 11, 2, '2021-05-28 23:39:17', NULL, '2021-05-31 12:30:41', 1, 1622223557347),
(41, 'Journal Voucher List', '', 'accounts/journal_voucher/get/voucher_list', 'Journal Voucher List', 'accounts', 'journal_voucher', 'view', 'acc_journal_voucher_list_view', 1, 28, 'Feature', 1, 11, 2, '2021-05-28 23:40:52', NULL, '2021-05-31 12:30:41', 1, 1622223652095),
(42, 'Transfer Voucher', '', '', '', '', NULL, '', '', 1, 24, 'Feature Group', 1, 12, 2, '2021-06-01 00:15:48', NULL, '2021-05-31 12:34:31', 1, 1622484948864),
(43, 'Transfer Voucher Create', '', '/accounts/transfer_voucher/save', 'Transfer Voucher Create', 'accounts', 'transfer_voucher', 'view', 'acc_transfer_voucher_add_view', 1, 42, 'Feature', 1, 12, 2, '2021-06-01 00:17:22', NULL, '2021-05-31 12:30:55', 1, 1622485042397),
(44, 'Transfer Voucher List', '', '/accounts/transfer_voucher/get/voucher_list', '', 'accounts', 'transfer_voucher', 'view', 'acc_transfer_voucher_list_view', 1, 42, 'Feature', 1, 12, 2, '2021-06-01 00:18:49', NULL, '2021-05-31 12:30:55', 1, 1622485129475),
(45, 'Transfer Voucher Update', '', '/accounts/transfer_voucher/update', '', 'accounts', 'transfer_voucher', 'view', 'acc_transfer_voucher_update_view', 0, 42, 'Feature', 1, 12, 2, '2021-06-01 00:20:01', 2, '2021-05-31 12:35:45', 1, 1622486145520),
(46, 'General Settings', '', '', 'General Settings', '', NULL, '', '', 1, NULL, 'Module', 1, 2, 2, '2021-06-01 12:03:30', 2, '2021-06-09 22:19:23', 1, 1623298763987),
(47, 'Bank Info', '', '', '', '', NULL, '', '', 1, 46, 'Feature Group', 1, 21, 2, '2021-06-01 12:04:22', NULL, '2021-06-01 00:04:22', 1, 1622527462651),
(48, 'Bank Account Create', '', '/application_common/bank_info/save', 'Bank Account Create', 'application_common', 'bank_info', 'view', 'bank_info_add_view', 1, 47, 'Feature', 1, 21, 2, '2021-06-01 12:08:32', 2, '2021-06-01 05:35:33', 1, 1622547333709),
(49, 'Bank Account Update', '', '/application_common/bank_info/update', '', 'application_common', 'bank_info', 'view', 'bank_info_update_view', 0, 47, 'Feature', 1, 21, 2, '2021-06-01 12:31:20', 2, '2021-06-01 05:35:45', 1, 1622547345525),
(50, 'Bank Account List', '', '/application_common/bank_info/list', '', 'application_common', 'bank_info', 'view', 'bank_info_list_view', 1, 47, 'Feature', 1, 21, 2, '2021-06-01 12:32:32', 2, '2021-06-01 05:35:59', 1, 1622547359214),
(54, 'Customer Info', '', '', '', '', NULL, '', '', 1, 46, 'Feature Group', 1, 22, 2, '2021-06-02 20:32:11', NULL, '2021-06-02 08:32:11', 1, 1622644331262),
(55, 'Customer Create', '', '/application_common/customer_info/save', '', 'application_common', 'customer_info', 'view', 'customer_info_add_view', 1, 54, 'Feature', 1, 22, 2, '2021-06-02 20:33:49', NULL, '2021-06-02 08:33:49', 1, 1622644429154),
(56, 'Customer Update', '', '/application_common/customer_info/update', '', 'application_common', 'customer_info', 'view', 'customer_info_update_view', 0, 54, 'Feature', 1, 23, 2, '2021-06-02 20:35:17', NULL, '2021-06-02 08:35:17', 1, 1622644517868),
(57, 'Customer List', '', '/application_common/customer_info/list', '', 'application_common', 'customer_info', 'view', 'customer_info_list_view', 1, 54, 'Feature', 1, 22, 2, '2021-06-02 20:36:29', NULL, '2021-06-02 08:36:29', 1, 1622644589959),
(58, 'Supplier Info', '', '', '', '', NULL, '', '', 1, 46, 'Feature Group', 1, 23, 2, '2021-06-02 21:14:43', NULL, '2021-06-02 09:14:43', 1, 1622646883726),
(59, 'Supplier Create', '', '/application_common/supplier_info/save', '', 'application_common', 'supplier_info', 'view', 'supplier_info_add_view', 1, 58, 'Feature', 1, 23, 2, '2021-06-02 21:18:14', NULL, '2021-06-02 09:18:14', 1, 1622647094476),
(60, 'Supplier Update', '', '/application_common/supplier_info/update', '', 'application_common', 'supplier_info', 'view', 'supplier_info_update_view', 0, 58, 'Feature', 1, 23, 2, '2021-06-02 21:20:00', NULL, '2021-06-02 09:20:00', 1, 1622647200196),
(61, 'Supplier List', '', '/application_common/supplier_info/list', '', 'application_common', 'supplier_info', 'view', 'supplier_info_list_view', 1, 58, 'Feature', 1, 23, 2, '2021-06-02 21:21:00', NULL, '2021-06-02 09:21:00', 1, 1622647260216),
(62, 'Employee Info', '', '', '', '', NULL, '', '', 1, 46, 'Feature Group', 1, 24, 2, '2021-06-02 21:22:02', NULL, '2021-06-02 09:22:02', 1, 1622647322035),
(63, 'Employee Create', '', '/application_common/employee_info/save', '', 'application_common', 'employee_info', 'view', 'employee_info_add_view', 1, 62, 'Feature', 1, 24, 2, '2021-06-02 21:23:17', NULL, '2021-06-02 09:23:17', 1, 1622647397470),
(64, 'Employee Update', '', '/application_common/employee_info/update', '', 'application_common', 'employee_info', 'view', 'employee_info_update_view', 0, 62, 'Feature', 1, 24, 2, '2021-06-02 21:24:39', NULL, '2021-06-02 09:24:39', 1, 1622647479871),
(65, 'Employee List', '', '/application_common/employee_info/list', '', 'application_common', 'employee_info', 'view', 'employee_info_list_view', 1, 62, 'Feature', 1, 24, 2, '2021-06-02 21:25:33', NULL, '2021-06-02 09:25:33', 1, 1622647533658),
(66, 'Voucher Posting', '', '', '', '', NULL, '', '', 1, 24, 'Feature Group', 1, 13, 2, '2021-06-03 12:11:04', 2, '2021-06-09 23:35:08', 1, 1623303308288),
(67, 'Voucher Post', '', '/accounts/voucher_post/posting', '', 'accounts', 'voucher_post', 'view', 'acc_voucher_posting_list_view', 1, 66, 'Feature', 1, 25, 2, '2021-06-03 12:13:40', 2, '2021-06-06 11:23:28', 1, 1623000208480),
(68, 'Report', '', '', '', '', NULL, '', '', 1, NULL, 'Module', 1, 10, 2, '2021-06-05 11:48:22', 2, '2021-06-04 23:55:02', 1, 1622872502490),
(69, 'Ledger', '', '', '', '', NULL, '', '', 1, 68, 'Feature Group', 1, 8, 2, '2021-06-05 11:50:43', NULL, '2021-06-04 23:50:43', 1, 1622872243368),
(70, 'Account Code Wise Ledger', '', 'accounts/report/account_ledger', '', 'accounts', 'report', 'view', 'acc_report_account_wise_form_view', 1, 69, 'Feature', 1, 8, 2, '2021-06-05 11:54:01', NULL, '2021-06-04 23:54:01', 1, 1622872441260),
(71, 'Sub Account Ledger', '', 'accounts/report/sub_account_ledger', '', 'accounts', 'report', 'view', 'acc_report_sub_account_wise_form_view', 1, 69, 'Feature', 1, 9, 2, '2021-06-06 21:56:12', 2, '2021-06-06 10:00:20', 1, 1622995220122),
(72, 'Sub Type Wise Ledger', '', 'accounts/report/sub_account_type_ledger', '', 'accounts', 'report', 'view', 'acc_report_cus_sup_emp_wise_form_view', 1, 69, 'Feature', 1, 10, 2, '2021-06-06 23:35:23', NULL, '2021-06-06 11:35:23', 1, 1623000923640),
(73, 'Bank Ledger', '', 'accounts/report/bank_ledger', '', 'accounts', 'report', 'view', 'acc_report_bank_form_view', 1, 69, 'Feature', 1, 11, 2, '2021-06-09 22:06:39', NULL, '2021-06-09 10:06:39', 1, 1623254799445),
(74, 'Clear Session', '', '/user_auth/user/view,/user_auth/user/session/get/all,/user_auth/user/session/get/{id},/user_auth/user/session/remove/{id}', '', 'user_auth', 'user', 'view', 'clear_user_session', 0, 15, 'Feature', 1, 7, 1, '2021-06-10 22:17:18', NULL, '2021-06-10 10:17:18', 1, 1623341837995),
(75, 'Trial Balance', '', '', '', '', NULL, '', '', 1, 68, 'Feature Group', 1, 9, 2, '2021-06-16 10:52:03', NULL, '2021-06-15 22:52:03', 1, 1623819123732),
(76, 'Trial Balance', '', 'accounts/report/trial_balance', '', 'accounts', 'report', 'view', 'acc_trial_balance_form_view', 1, 75, 'Feature', 1, 1, 2, '2021-06-16 10:53:40', NULL, '2021-06-15 22:53:40', 1, 1623819220196),
(77, 'Inventory', '', '', 'Inventory', '', NULL, '', '', 1, NULL, 'Module', 1, 11, 2, '2025-06-30 20:18:08', NULL, '2025-06-30 08:42:06', 1, 1751293088395),
(78, 'Item Master', '', '', 'Item master', '', NULL, '', '', 1, 77, 'Feature Group', 1, 1, 2, '2025-06-30 20:19:41', NULL, '2025-06-30 08:19:41', 1, 1751293181097),
(79, 'Item List', '', '/inventory/item-master/list', '', 'inventory', 'item-master', 'view', 'item_master_list_view', 1, 78, 'Feature', 1, 2, 2, '2025-06-30 20:23:14', 2, '2025-08-04 18:15:12', 1, 1754331312546),
(80, 'Item Create', '', '/inventory/item-master/save', '', 'inventory', 'item-master', 'view', 'item_master_add_view', 1, 78, 'Feature', 1, 1, 2, '2025-07-06 20:19:26', 2, '2025-08-04 18:15:24', 1, 1754331324538),
(81, 'Item Update', '', '/inventory/item-master/update', '', 'inventory', 'item-master', 'view', 'item_master_update_view', 0, 78, 'Feature', 1, 32, 2, '2025-07-06 20:20:49', 2, '2025-07-06 14:28:09', 1, 1751812089870),
(82, 'Codes', '', '', '', '', NULL, '', '', 1, 46, 'Feature Group', 1, 15, 1, '2025-07-07 00:52:16', 1, '2025-07-06 18:54:03', 1, 1751828043068),
(83, 'Code List', '', '/application_common/app_codes/list', '', 'application_common', 'app_codes', 'view', 'app_codes_list_view', 1, 82, 'Feature', 1, 42, 1, '2025-07-07 00:56:19', NULL, '2025-07-07 06:54:40', 1, 1751828179701),
(84, 'Code Create', '', '/application_common/app_codes/save', '', 'application_common', 'app_codes', 'view', 'app_codes_add_view', 1, 82, 'Feature', 1, 41, 1, '2025-07-07 01:03:21', NULL, '2025-07-07 06:54:46', 1, 1751828601952),
(85, 'Codes Update', '', '/application_common/app_codes/update', '', 'application_common', 'app_codes', 'view', 'app_codes_update_view', 0, 82, 'Feature', 1, 43, 1, '2025-07-07 01:05:24', 1, '2025-07-06 19:05:45', 1, 1751828745693),
(86, 'Item Receive', '', '', '', '', NULL, '', '', 1, 77, 'Feature Group', 1, 1, 1, '2025-07-09 14:51:09', 1, '2025-07-09 08:57:11', 1, 1752051431938),
(87, 'Item Receive List', '', '/inventory/inventory-movement/list', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_list_view', 1, 86, 'Feature', 1, 2, 1, '2025-07-09 14:53:10', 1, '2025-07-09 08:56:40', 1, 1752051400712),
(88, 'Item Receive Create', '', '/inventory/inventory-movement/save', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_add_view', 1, 86, 'Feature', 1, 1, 1, '2025-07-09 14:56:07', 2, '2025-07-16 04:54:57', 1, 1752641697800),
(89, 'Item Receive Update', '', '/inventory/inventory-movement/update', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_update_view', 0, 86, 'Feature', 1, 3, 1, '2025-07-09 14:59:01', 1, '2025-07-09 08:59:17', 1, 1752051557310),
(90, 'Item Issue Create', '', '/inventory/inventory-movement/save', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_issue_add_view', 1, 91, 'Feature', 1, 1, 2, '2025-07-16 11:31:24', 2, '2025-07-16 05:33:49', 1, 1752644029888),
(91, 'Item Issue', '', '', '', '', NULL, '', '', 1, 77, 'Feature Group', 1, 2, 1, '2025-07-16 11:33:19', NULL, '2025-07-16 05:33:19', 1, 1752643999372),
(92, 'Item Issue List', '', '/inventory/inventory-movement/list', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_issue_list_view', 1, 91, 'Feature', 1, 2, 2, '2025-07-16 11:35:07', 2, '2025-07-16 05:36:54', 1, 1752644214395),
(93, 'Item Issue Update', '', '/inventory/inventory-movement/update', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_issue_update_view', 0, 91, 'Feature', 1, 3, 2, '2025-07-16 11:36:17', NULL, '2025-07-16 05:36:17', 1, 1752644177375),
(94, 'Item Transfer Create', '', '/inventory/inventory-movement/save', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_transfer_add_view', 1, 95, 'Feature', 1, 1, 1, '2025-07-16 23:48:33', 1, '2025-07-16 17:49:37', 1, 1752688177423),
(95, 'Item Transfer', '', '', '', '', NULL, '', '', 1, 77, 'Feature Group', 1, 4, 1, '2025-07-16 23:49:24', NULL, '2025-07-16 17:49:24', 1, 1752688164010),
(96, 'Item Transfer List', '', '/inventory/inventory-movement/list', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_transfer_list_view', 1, 95, 'Feature', 1, 2, 1, '2025-07-16 23:51:10', NULL, '2025-07-16 17:51:10', 1, 1752688270305),
(97, 'Item Transfer Update', '', '/inventory/inventory-movement/update', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_transfer_update_view', 0, 95, 'Feature', 1, 3, 1, '2025-07-16 23:52:31', NULL, '2025-07-16 17:52:31', 1, 1752688351096),
(98, 'Inventory Posting', '', '', '', '', NULL, '', '', 1, 77, 'Feature Group', 1, 7, 1, '2025-07-20 10:55:10', 1, '2025-07-20 05:01:14', 1, 1752987674856),
(99, 'Inventory Posting', '', '/inventory/inventory-movement/posting', '', 'inventory', 'inventory-movement', 'view', 'inventory_movement_posting_list_view', 1, 98, 'Feature', 1, 1, 1, '2025-07-20 11:00:47', 2, '2025-07-30 16:07:49', 1, 1753891669593),
(101, 'Report', '', '', '', '', NULL, '', '', 1, 77, 'Feature Group', 1, 7, 1, '2025-07-26 11:54:17', NULL, '2025-07-26 05:54:17', 1, 1753509257936),
(102, 'Item Ledger', '', 'inventory/report/item-ledger', '', 'inventory', 'report', 'view', 'inventory_movement_item_ledger_form_view', 1, 101, 'Feature', 1, 1, 1, '2025-07-26 11:58:58', NULL, '2025-07-26 05:58:58', 1, 1753509538976),
(103, 'Financial Report', '', '', '', '', NULL, '', '', 1, 68, 'Feature Group', 1, 10, 1, '2025-07-30 09:20:21', NULL, '2025-07-30 03:27:03', 1, 1753845621610),
(104, 'Balance Sheet', '', 'accounts/report/balance_sheet', '', 'accounts', 'report', 'view', 'acc_balance_sheet_form_view', 1, 103, 'Feature', 1, 1, 1, '2025-07-30 09:24:12', NULL, '2025-07-30 03:24:12', 1, 1753845852679),
(105, 'Income Statement', '', 'accounts/report/income_statement', '', 'accounts', 'report', 'view', 'acc_income_statement_form_view', 1, 103, 'Feature', 1, 2, 2, '2025-07-30 16:50:45', NULL, '2025-07-30 10:50:45', 1, 1753872645769);

--
-- Dumping data for table `item_master`
--

INSERT INTO `item_master` (`id`, `create_on`, `created_by`, `organization_id`, `updated_by`, `updated_on`, `active`, `brand`, `category`, `color`, `description`, `imei`, `item_code`, `item_name`, `model`, `price`, `standard_cost`, `standard_price`, `storage`, `unit`) VALUES
(15, NULL, NULL, 1, NULL, NULL, b'1', '', 'Mobile', 'Black', '', '', 'MOBL0001', 'Samsung Galaxy M56', '', 50000.00, NULL, 40000.00, '', ''),
(16, NULL, NULL, 1, NULL, NULL, b'1', 'Samsung', 'Mobile', NULL, '', '', 'MOBL0002', 'Samsung Galaxy Z Fold4 5G', '', NULL, NULL, NULL, '', ''),
(17, NULL, 2, 1, NULL, NULL, b'1', 'Samsung', 'Mobile', NULL, '', '', 'MOBL0003', 'Galaxy Tab S6 Lite (4/64 GB)', '', 40000.00, NULL, NULL, '', NULL),
(18, NULL, 2, 1, NULL, NULL, b'1', 'Nokia', 'Mobile', NULL, '', '', 'MOBL0004', 'Nokia G21 - Official', '', 19000.00, NULL, NULL, '', NULL),
(19, NULL, 2, 1, NULL, NULL, b'1', 'Nokia', 'Mobile', NULL, '', '', 'MOBL0005', 'Nokia G10 - Official', '', NULL, NULL, NULL, '', NULL),
(20, NULL, 2, 1, NULL, NULL, b'1', 'Walton', 'Mobile', NULL, '', '', 'MOBL0006', 'XANON X91', '', 30999.00, NULL, NULL, '', NULL),
(21, NULL, 2, 1, NULL, NULL, b'1', 'Walton', 'Mobile', NULL, '', '', 'MOBL0007', 'NEXG N10 ULTRA', '', 17208.00, NULL, NULL, '', NULL),
(22, NULL, 2, 1, NULL, NULL, b'1', 'Huawei', 'Mobile', NULL, '', '', 'MOBL0008', 'Huawei Pura 80', '', 30000.00, NULL, NULL, '', NULL),
(23, NULL, 2, 1, NULL, NULL, b'1', 'Huawei', 'Mobile', NULL, '', '', 'MOBL0009', 'Huawei Nova Y73', '', 25000.00, NULL, NULL, '', NULL),
(24, NULL, 2, 1, NULL, NULL, b'1', 'Huawei', 'Mobile', NULL, '', '', 'MOBL0010', 'Huawei Nova 14 Ultra', '', 70000.00, NULL, NULL, '', NULL),
(25, NULL, 2, 1, NULL, NULL, b'1', 'Huawei', 'Mobile', NULL, '', '', 'MOBL0011', 'Huawei Nova 14 Pro', '', 60000.00, NULL, NULL, '', NULL),
(26, NULL, 2, 1, NULL, NULL, b'1', 'Huawei', 'Mobile', NULL, '', '', 'MOBL0012', 'Huawei MatePad Pro 13.2 (2025)', '', NULL, NULL, NULL, '', NULL);

--
-- Dumping data for table `organization`
--

INSERT INTO `organization` (`id`, `address1`, `address2`, `contact_name`, `contact_phone`, `create_on`, `created_by`, `email`, `is_active`, `logo`, `name`, `short_name`, `title`, `updated_by`, `updated_on`, `web_url`) VALUES
(1, 'Banasree, Rampura, Dhaka.', NULL, NULL, NULL, NULL, NULL, NULL, b'1', NULL, 'ZARA Fashion', NULL, NULL, NULL, NULL, NULL);

--
-- Dumping data for table `role_feature`
--

INSERT INTO `role_feature` (`role_feature_id`, `role_id`, `feature_id`, `is_home`, `created_by`, `created_on`, `updated_by`, `updated_on`, `active`, `version_no`) VALUES
(1, 1, 1, 0, NULL, NULL, NULL, '2019-06-01 21:27:38', b'1', 1),
(2, 1, 2, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(3, 1, 4, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(4, 1, 5, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(5, 1, 6, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(6, 1, 7, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(7, 1, 8, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(8, 1, 3, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(9, 1, 9, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(10, 1, 11, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(11, 1, 12, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(12, 1, 10, 0, NULL, NULL, 1, '2021-04-22 01:35:38', b'1', 1),
(13, 1, 13, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(14, 1, 14, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(15, 1, 15, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(16, 1, 20, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(17, 1, 16, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(18, 1, 19, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(19, 1, 21, 0, NULL, NULL, NULL, '2019-06-01 21:27:47', b'1', 1),
(20, 1, 22, 0, NULL, NULL, NULL, '2021-04-22 01:35:22', b'1', 1),
(21, 1, 23, 1, NULL, NULL, NULL, '2021-04-22 01:35:38', b'1', 1),
(2374, 5, 1, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2375, 5, 2, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2376, 5, 8, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2377, 5, 7, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2378, 5, 5, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2379, 5, 4, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2380, 5, 6, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2381, 5, 3, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2382, 5, 13, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2383, 5, 12, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2384, 5, 9, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2385, 5, 14, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2386, 5, 10, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2387, 5, 11, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2388, 5, 15, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2389, 5, 74, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2390, 5, 19, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2391, 5, 20, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2392, 5, 16, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2393, 5, 21, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2394, 5, 22, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2395, 5, 23, 1, NULL, NULL, 1, '2025-07-30 10:52:47', b'1', 1753872767067),
(2396, 5, 24, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2397, 5, 25, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2398, 5, 26, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2399, 5, 36, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2400, 5, 37, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2401, 5, 27, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2402, 5, 29, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2403, 5, 38, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2404, 5, 39, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2405, 5, 28, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2406, 5, 30, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2407, 5, 41, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2408, 5, 40, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2409, 5, 31, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2410, 5, 32, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2411, 5, 33, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2412, 5, 34, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2413, 5, 35, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2414, 5, 42, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2415, 5, 43, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2416, 5, 44, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2417, 5, 45, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2418, 5, 46, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2419, 5, 47, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2420, 5, 48, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2421, 5, 50, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2422, 5, 49, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2423, 5, 54, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2424, 5, 55, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2425, 5, 57, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2426, 5, 56, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2427, 5, 58, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2428, 5, 59, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2429, 5, 61, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2430, 5, 60, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2431, 5, 62, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2432, 5, 63, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2433, 5, 65, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2434, 5, 64, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2435, 5, 66, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2436, 5, 67, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2437, 5, 68, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2438, 5, 69, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2439, 5, 70, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2440, 5, 71, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2441, 5, 72, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2442, 5, 73, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2443, 5, 75, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2444, 5, 76, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2445, 5, 77, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2446, 5, 78, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2447, 5, 79, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2448, 5, 80, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2449, 5, 81, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2450, 5, 82, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2451, 5, 84, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2452, 5, 83, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2453, 5, 85, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2454, 5, 86, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2455, 5, 88, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2456, 5, 87, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2457, 5, 89, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2458, 5, 91, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2459, 5, 90, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2460, 5, 92, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2461, 5, 93, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2462, 5, 95, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2463, 5, 94, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2464, 5, 96, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2465, 5, 97, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2466, 5, 98, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2467, 5, 99, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2468, 5, 101, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2469, 5, 102, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2470, 5, 103, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2471, 5, 104, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2472, 5, 105, 0, NULL, NULL, NULL, '2025-07-30 10:52:47', b'1', 1753872767034),
(2473, 6, 21, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2474, 6, 22, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2475, 6, 23, 1, NULL, NULL, 1, '2025-07-30 10:52:54', b'1', 1753872774216),
(2476, 6, 24, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2477, 6, 25, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2478, 6, 26, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2479, 6, 36, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2480, 6, 37, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2481, 6, 27, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2482, 6, 29, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2483, 6, 38, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2484, 6, 39, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2485, 6, 28, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2486, 6, 30, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2487, 6, 41, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2488, 6, 40, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2489, 6, 42, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2490, 6, 43, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2491, 6, 44, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2492, 6, 45, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2493, 6, 66, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2494, 6, 67, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2495, 6, 68, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2496, 6, 69, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2497, 6, 70, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2498, 6, 71, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2499, 6, 72, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2500, 6, 73, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2501, 6, 75, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2502, 6, 76, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2503, 6, 77, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2504, 6, 78, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2505, 6, 79, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2506, 6, 80, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2507, 6, 81, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2508, 6, 86, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2509, 6, 88, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2510, 6, 87, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2511, 6, 89, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2512, 6, 91, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2513, 6, 90, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2514, 6, 92, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2515, 6, 93, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2516, 6, 95, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2517, 6, 94, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2518, 6, 96, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2519, 6, 97, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2520, 6, 98, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2521, 6, 99, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2522, 6, 101, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2523, 6, 102, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2524, 6, 103, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2525, 6, 104, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199),
(2526, 6, 105, 0, NULL, NULL, NULL, '2025-07-30 10:52:54', b'1', 1753872774199);

--
-- Dumping data for table `supplier_info`
--

INSERT INTO `supplier_info` (`id`, `address`, `chart_of_accounts_id`, `create_on`, `created_by`, `email`, `organization_id`, `phone`, `status`, `supplier_code`, `supplier_name`, `updated_by`, `updated_on`) VALUES
(1, 'banasree', 0, '2021-06-02 15:26:51', 2, 'rony@gmail.com', 1, '01730796779', b'1', 'SUP-0001', 'khalad rony', 2, '2021-06-02 15:27:07');

--
-- Dumping data for table `system_role`
--

INSERT INTO `system_role` (`role_id`, `role_name`, `role_code`, `note`, `created_by`, `created_on`, `updated_by`, `updated_on`, `active`, `version_no`) VALUES
(1, 'SuperAdmin', 'R0001', 'Super admin', 1, '2019-04-03 09:44:11', 1, '2019-06-02 03:35:39', 1, 1),
(5, 'Admin', 'admin001', 'admin access', 1, '2019-06-11 15:00:05', NULL, '2019-06-10 21:00:05', 1, 1),
(6, 'User', '1623339983447', '', 2, '2021-06-10 21:46:29', NULL, '2021-06-10 09:46:29', 1, 1623339989914);

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`user_id`, `first_name`, `last_name`, `user_code`, `usremail`, `phone`, `usrpkey`, `pkey_last_change`, `address`, `country_id`, `created_by`, `created_on`, `updated_by`, `updated_on`, `active`, `is_master`, `lan_id`, `password_updated_by`, `password_updated_on`, `is_default_password_change`) VALUES
(1, 'Md. Khalad', 'Mosharaf', 'rony', 'khalad.rony@gmail.com', '017303796779', '4a6fa4173a49a42da4653bd24adc242e1e22197db0792fca47ce61ead9ffd888eb6418074ec2faddf26f7c3e0d5a44cc2d2367aec6a416e1ef54b1921c5d94c0', '2020-03-01 16:34:33', 'Dhaka', 1, 1, '2019-04-03 09:38:39', 1, '2025-06-24 12:35:10', 1, 1, 'admin', NULL, '2021-04-21 22:33:59', 1),
(2, 'Khalad', 'Rony', '1112', 'rony@gmail.com', '01730796779', '4a6fa4173a49a42da4653bd24adc242e1e22197db0792fca47ce61ead9ffd888eb6418074ec2faddf26f7c3e0d5a44cc2d2367aec6a416e1ef54b1921c5d94c0', '2021-06-10 12:25:27', 'Banasree', 1, 1, '2021-04-25 21:36:53', 2, '2025-06-24 12:15:38', 1, 0, '1112', 2, '2021-06-10 00:25:27', 1),
(3, 'khalad', 'Mosharaf', '', 'ronykhalad@gmail.com', '01730796779', '4a6fa4173a49a42da4653bd24adc242e1e22197db0792fca47ce61ead9ffd888eb6418074ec2faddf26f7c3e0d5a44cc2d2367aec6a416e1ef54b1921c5d94c0', '2021-06-10 21:41:23', '', 1, 2, '2021-06-10 21:27:52', 2, '2021-06-10 09:48:18', 1, 0, '1000', 3, '2021-06-10 09:41:23', 1);

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_role_id`, `user_id`, `role_id`, `created_by`, `created_on`, `updated_by`, `updated_on`, `active`, `version_no`) VALUES
(1, 1, 1, 1, '2019-04-03 12:45:17', 1, '2019-06-02 04:17:24', 1, 1),
(3, 2, 5, NULL, NULL, NULL, '2021-05-27 11:53:17', 1, NULL),
(5, 3, 6, NULL, NULL, NULL, '2021-06-10 09:48:18', 1, NULL);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
