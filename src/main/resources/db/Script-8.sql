create database pln;

use pln;

select * from post;

drop table post;

create table post(
	id_post bigint auto_increment primary key,
	id varchar(100),
	username VARCHAR(100) not null,
	name VARCHAR(100) not null,
	fallowers integer,
	location VARCHAR(255),
	postid bigint,
	isRetweet BOOLEAN,
	message TEXT(500),
	postdate date
)