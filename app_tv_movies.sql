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

CREATE TABLE tv_show_created_by_user (
	id_tv_show_created_by_user int auto_increment not null,
    name_tv_show varchar(50) not null,
    genre varchar(50) not null,
    production_company varchar(50) not null,
    constraint pk_id_tv_show_created_by_user primary key (id_tv_show_created_by_user)
);
DROP TABLE tv_show_created_by_user;

CREATE TABLE tv_show_reminder (
	id_tv_show_reminder int auto_increment not null,
    id_user int not null,
    id_basic_tv_show_info int,
    id_tv_show_created_by_user	int,
    completed boolean,
    current_season int,
    current_episode int,
    personal_rating int,
	constraint pk_id_tv_show_reminder primary key (id_tv_show_reminder),
    constraint fk_id_user foreign key (id_user) references user (id_user),
    constraint fk_id_basic_tv_show_info foreign key (id_basic_tv_show_info) references basic_tv_show_info (id_basic_tv_show_info),
	constraint fk_id_tv_show_created_by_user foreign key (id_tv_show_created_by_user) references tv_show_created_by_user (id_tv_show_created_by_user)
);
DROP TABLE tv_show_reminder;
select * from tv_show_reminder;

# Trigger to check that we can not insert a tv show reminder if we have two null values at the same 
# time in the id_basic_tv_show_info and id_tv_show_created_by_user columns.

DELIMITER //
CREATE TRIGGER bi_tv_show_reminder_check_nulls_in_fks BEFORE INSERT ON tv_show_reminder FOR EACH ROW
BEGIN
  IF (NEW.id_basic_tv_show_info IS NULL AND NEW.id_tv_show_created_by_user IS NULL) THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = '\'Field id_basic_tv_show_info \' and \'Field id_tv_show_created_by_user \' cannot both be null';
  END IF;
END//
CREATE TRIGGER bu_tv_show_reminder_check_nulls_in_fks BEFORE UPDATE ON tv_show_reminder FOR EACH ROW
BEGIN
  IF (NEW.id_basic_tv_show_info IS NULL AND NEW.id_tv_show_created_by_user IS NULL) THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = '\'Field id_basic_tv_show_info \' and \'Field id_tv_show_created_by_user \' cannot both be null';
  END IF;
END//
DELIMITER ;

##############
DROP TRIGGER bi_tv_show_reminder_check_nulls_in_fks;
DROP TRIGGER bu_tv_show_reminder_check_nulls_in_fks;
show triggers;
#############









#####
select * from tv_show_created_by_user;
select * from user;
delete from tv_show_created_by_user where id_tv_show_created_by_user = 1;
select * from basic_tv_show_info where original_name = "Lost";

#####
select count(*) from basic_movie_info;
select count(*) from basic_tv_show_info;
select * from basic_movie_info;
select * from basic_tv_show_info;
#####

select * from basic_tv_show_info where original_name = 'Stranger Things';
select * from basic_tv_show_info where original_name = 'Friday Night Lights';


select * from basic_movie_info where original_title = 'L’ATALANTE ';





