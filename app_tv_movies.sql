CREATE DATABASE app_tv_movies;
DROP DATABASE app_tv_movies;
USE app_tv_movies;

CREATE TABLE user(
	id_user 				int auto_increment not null,
    first_name 				varchar(50) not null,
    last_name				varchar(50) not null,
    username				varchar(50) not null,
    password	    		varchar(50) not null,
	constraint pk_id_client primary key (id_user)	
);
drop table users;
INSERT INTO user (first_name, last_name, username, password) VALUES ("Felipe", "Demaria", "dema22", "gildeverga");