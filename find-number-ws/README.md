Database schema.
Execute follow queries to create database schema

create table file(
  id serial primary key,
  name varchar(200) not null unique
);

create table number_files(
  number numeric,
  file_id int4 not null references file
);

since files are big apply index.
create index number_inx on number_files using btree(number);