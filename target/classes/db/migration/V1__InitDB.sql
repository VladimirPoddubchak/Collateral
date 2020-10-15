create table if not exists CAR (
    id  BIGSERIAL not null,
    brand VARCHAR(150),
    model VARCHAR(200),
    power FLOAT,
    assessed_value NUMERIC(19, 2),
    year_of_issue SMALLINT,
    primary key (id));

insert into CAR (brand, model, power, assessed_value, year_of_issue)
    values ('Toyota','Prius',2500,1500000,2010),
       ('Toyota','Mark2',2500,1000000,2008),
       ('Nissan','Safari',4000,1200000,2005),
       ('Honda','Accord',2500,1300000,2015);