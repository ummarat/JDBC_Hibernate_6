create database db;
use db;
create table author
(
    id int auto_increment
        primary key,
    name varchar(45)  null,
    last_name varchar(255) null
);

create table book
(
    id int auto_increment,
    name varchar(45) null,
    primary key (id),
    author_id int,
    foreign key (author_id) references author (id)
);

insert into author (name, last_name)
    value ('Taras', 'Shevchenko'), ('Vasyl', 'Stus'), ('Vasyl', 'Symonenko'), ('Marek', 'Dvorjevski');

insert into book (name,author_id)
    value ('Zapovit', 1), ('Krugovert', 2), ('Zbirka', 3), ('Doroga v nebo', 4);

drop database db;