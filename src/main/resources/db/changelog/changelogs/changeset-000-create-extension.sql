--liquibase formatted sql

--changeset Stefan:createUuidOsspExtension runAlways:true
--comment: Creating uuid-ossp extension
--preConditions onFail:MARK_RAN

--sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--changeset Stefan:setSearchPath runAlways:true
--comment: Setting search path to include test_schema
--preConditions onFail:MARK_RAN

--sql
SET search_path TO test_schema, public;