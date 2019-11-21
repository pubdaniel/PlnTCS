create database pln;

use pln;

select * from post;
select * from user;
select * from query order by date desc;

select (count(id)/ 2 * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (  
select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%Carlos %" group by id  
union all  
select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%peruca%" group by id ) 
as result group by id order by relevance desc;


select sum(message), id_post, id, username, name, fallowers, location, postid, isRetweet, message, postdate from post where message like "%sequestra%"
group by id_post;

insert query 

select count(message) as soma, id_post, id, username, name, fallowers, location, postid, isRetweet, message, postdate from 
(
		select * from post where lower(message) like '%guerra%' 
	union all
		select * from post where lower(message) like '%vagabundo%'
) as resultado
group by message order by soma desc;


select count(id) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (
select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%floripa%" group by id
union all
select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%corassãum%" group by id
) as result group by id order by relevance desc;


select now();

 select (count(id)/ 1 * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%Floripa %" group by id ) as result group by id order by relevance desc;


 select (count(id)/ 1 * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%peruca%" group by id ) as result group by id order by relevance desc;


select (count(id)/ 2 * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (  
	select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%Floripa %" group by id  
	union all  
	select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%sol%" group by id 
	) as result group by id order by relevance desc;


select * from post where id="1027337015975731206"

select postdate from post order by postdate desc

select * from user where nickname ="admin" AND password="abc123"
select * from user where nickname ="admin" AND password="abc123";

select * from query where user_id=2

select * from post;

-- drop table post;

create table post(
	id_post bigint auto_increment primary key,
	id varchar(100),
	username VARCHAR(100) not null,
	name VARCHAR(100) not null,
	fallowers integer,
	location VARCHAR(100),
	postid bigint,
	isRetweet BOOLEAN,
	message TEXT(500),
	postdate datetime
)

create table user (
	id bigint not null auto_increment primary key,
	name varchar(50),
	nickname varchar(50),
	email varchar(50),
	phone varchar(50),
	password varchar(50),
	permission int,
	authkey varchar(25)
)


 select (count(id)/ 5 * 100) as relevance, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from (  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%Maria%" group by id  union all  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%arma%" group by id  union all  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where message like "%Floripa%" group by id  union all  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where location like "%Floripa%" group by id  union all  select count(1) as rel, id, id_post, username, name, message, fallowers, location, postid,isRetweet, postdate from post where location like "%Maria%" group by id ) as result group by id order by relevance desc limit 25;




select * from user

select * from query inner join user on user.id = query.user_id order by date desc limit 25 

alter table user add column authkey varchar(25)

insert into user (name, nickname, email, phone, password, permission) values ("root", "root", "daniel@pln.com", "48 987654321", "daniel123", 1)

insert into query (user_id, message, date, relevance) values (1 ,"bolsonaro presidente", now(), 65)

select * from query order by id desc;

select * from query inner join user on user.id = query.user_id order by query.id desc limit 200

delete from query where message like "%%"

date();

alter table query modify date DATETIME;

create table query (
	id bigint not null auto_increment primary key,
	user_id bigint,
	date DATETIME,
	message varchar(255),
	relevance int,
	foreign key (user_id) references user (id) 
)