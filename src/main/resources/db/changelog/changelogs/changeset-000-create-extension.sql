--liquibase formatted sql

--changeset Stefan:createUuidOsspExtension runAlways:true
--comment: Creating uuid-ossp extension
--preConditions onFail:MARK_RAN

--sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";