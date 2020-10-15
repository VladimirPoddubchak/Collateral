
create extension if not exists "uuid-ossp";
create table if not exists ASSESS (
    id  bigserial not null primary key,
    collateral_id UUID not null,
    collateral_type VARCHAR(100) check (collateral_type in ('CAR','AIRPLANE')),
    assessed_value NUMERIC(19, 2) not null,
    assess_date TIMESTAMPTZ not null,
    approve_status BOOLEAN);
