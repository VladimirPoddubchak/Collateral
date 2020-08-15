create table if not exists car (
    id  bigserial not null,
    brand varchar(255),
    model varchar(255),
    power float8, assessed_value numeric(19, 2),
    year_of_issue int2,
    primary key (id));
