create table if not exists AIRPLANE(
    id UUID default uuid_generate_v4 () primary key,
    brand VARCHAR(150),
    model VARCHAR(200),
    manufacturer VARCHAR(500),
    year_of_issue SMALLINT,
    fuel_capacity INT,
    seats INT);



