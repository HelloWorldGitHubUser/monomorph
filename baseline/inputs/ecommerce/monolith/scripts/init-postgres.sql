-- ============================================================
-- PostgreSQL: Create the monolith database on localhost:5432
-- Run with: psql -h localhost -p 5432 -U admin -f init-postgres.sql
-- ============================================================

SELECT 'CREATE DATABASE ecommerce' WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'ecommerce')\gexec
