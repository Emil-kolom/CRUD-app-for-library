create table if not exists person (
        id int generated always as IDENTITY primary key,
        full_name varchar(150) UNIQUE,
        year_birth int,
        created_at date
);
create table if not exists book(
        id int generated always as IDENTITY primary key,
        title varchar(100) not null ,
        author varchar(100),
        year int,
        person_id int references person(id) on delete set null
);