alter table CAR drop column id;
alter table CAR add column id UUID DEFAULT uuid_generate_v4 () PRIMARY KEY;

insert into ASSESS (collateral_id,collateral_type, assessed_value, assess_date, approve_status)
    select id,'CAR',assessed_value, now(),'true' from CAR;

alter table CAR drop column assessed_value;


