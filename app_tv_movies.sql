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
DROP TABLE user;

CREATE TABLE basic_tv_show_info (
	id_basic_tv_show_info 	    		int not null,
    original_name 						varchar(500) not null,
	constraint pk_id_basic_tv_show_info primary key (id_basic_tv_show_info)	
);
DROP TABLE basic_tv_show_info;


CREATE TABLE basic_movie_info (
	id_basic_movie_info 	    		int not null,
    original_title 						varchar(500) not null,
	constraint pk_id_basic_movie_info primary key (id_basic_movie_info)	
);
DROP TABLE basic_movie_info;
show tables



#INSERT INTO user (first_name, last_name, username, password) VALUES ("Felipe", "Demaria", "dema22", "gildeverga");