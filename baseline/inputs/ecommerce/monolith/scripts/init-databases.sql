-- ============================================================
-- MySQL: Create the monolith database on localhost:3307
-- Run with: mysql -h localhost -P 3307 -u root -p12042003 < init-databases.sql
-- ============================================================

CREATE DATABASE IF NOT EXISTS ecommerce DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Seed user roles (required before user registration)
USE ecommerce;
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(60) NOT NULL UNIQUE
);
INSERT IGNORE INTO roles (roleName) VALUES ('USER'), ('PM'), ('ADMIN');
