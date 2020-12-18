CREATE DATABASE app_tv_movies;
DROP DATABASE app_tv_movies;
USE app_tv_movies;
SHOW VARIABLES LIKE 'foreign_key_checks';
SHOW CREATE TABLE tv_show_reminder;

##
select @@FOREIGN_KEY_CHECKS;
set FOREIGN_KEY_CHECKS=1;
##

CREATE TABLE user(
	id_user 				int auto_increment not null,
    first_name 				varchar(50) not null,
    last_name				varchar(50) not null,
    username				varchar(50) not null,
    password	    		varchar(150) not null,
    email					varchar(50) not null,
    id_role					int not null,
	constraint pk_id_client primary key (id_user),
	constraint fk_id_role foreign key (id_role) references user_role (id_role),
    constraint unique_username UNIQUE (username)
)ENGINE = InnoDB;
DROP TABLE user;

## Inserts
INSERT INTO USER(first_name, last_name, username, password, email, id_role)
VALUES ("feli", "dema" , "dema", "respeta", "felipedemaria@hotmail.com", 1);

INSERT INTO USER(first_name, last_name, username, password, email, id_role)
VALUES ("gaspar", "tripodi" , "pila", "respeta", "pila@hotmail.com", 1);

INSERT INTO USER(first_name, last_name, username, password, email, id_role)
VALUES ("octa", "iogha" , "admin", "admin", "admin@hotmail.com", 1);


CREATE TABLE user_role (
	id_role			    int auto_increment not null,
    role_name			varchar(50) not null,
    constraint  pk_id_role primary key (id_role),
	constraint unique_role_name UNIQUE (role_name)
)ENGINE = InnoDB;
DROP TABLE user_role;

## Inserts
INSERT INTO user_role (role_name) VALUES ("ROLE_USER");
INSERT INTO user_role (role_name) VALUES ("ROLE_ADMIN");


CREATE TABLE basic_tv_show_info (
	id_basic_tv_show_info 	    		int not null,
    original_name 						varchar(500) not null,
	constraint pk_id_basic_tv_show_info primary key (id_basic_tv_show_info)	
)ENGINE = InnoDB;
DROP TABLE basic_tv_show_info;

CREATE TABLE tv_show_created_by_user (
	id_tv_show_created_by_user int auto_increment not null,
    id_user int not null,
    name_tv_show varchar(50) not null,
    genre varchar(50),
    production_company varchar(50),
    constraint pk_id_tv_show_created_by_user primary key (id_tv_show_created_by_user),
	constraint fk_id_user_that_created_the_show foreign key (id_user) references user (id_user),
    constraint unique_name_tv_show UNIQUE (name_tv_show)
)ENGINE = InnoDB;
DROP TABLE tv_show_created_by_user;

## Inserts 
INSERT INTO tv_show_created_by_user (id_user, name_tv_show, genre, production_company)
VALUES (1,"chiquititas", "comedia", "telefe");

INSERT INTO tv_show_created_by_user (id_user, name_tv_show, genre, production_company)
VALUES (1,"okupas", "comedia", "telefe");

INSERT INTO tv_show_created_by_user (id_user, name_tv_show, genre, production_company)
VALUES (2, "son amores", "comedia", "telefe");

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
	constraint fk_id_tv_show_created_by_user foreign key (id_tv_show_created_by_user) references tv_show_created_by_user (id_tv_show_created_by_user) ON DELETE CASCADE
)ENGINE = InnoDB;
DROP TABLE tv_show_reminder;

## Inserts
INSERT INTO tv_show_reminder (id_user, id_basic_tv_show_info, id_tv_show_created_by_user, completed, current_season, current_episode, personal_rating)
VALUES (1, null, 1, 0, 10, 20, 5);

INSERT INTO tv_show_reminder (id_user, id_basic_tv_show_info, id_tv_show_created_by_user, completed, current_season, current_episode, personal_rating)
VALUES (1, null, 3, 0, 10, 20, 5);

INSERT INTO tv_show_reminder (id_user, id_basic_tv_show_info, id_tv_show_created_by_user, completed, current_season, current_episode, personal_rating)
VALUES (1, 1438, null, 0, 10, 20, 5);

INSERT INTO tv_show_reminder (id_user, id_basic_tv_show_info, id_tv_show_created_by_user, completed, current_season, current_episode, personal_rating)
VALUES (2, null, 4, 0, 10, 20, 5);

INSERT INTO tv_show_reminder (id_user, id_basic_tv_show_info, id_tv_show_created_by_user, completed, current_season, current_episode, personal_rating)
VALUES (2, 1438, null, 0, 10, 20, 5);

################### TRIGGERS ####################

## Trigger that when deleting a record from the tv show reminder table, if the reminder has a tv show created
## by user associated, we will delete this record.

DELIMITER //
CREATE TRIGGER delete_user_tv_show BEFORE DELETE ON tv_show_reminder FOR EACH ROW
BEGIN
  IF (OLD.id_tv_show_created_by_user IS NOT NULL) THEN
    DELETE FROM tv_show_created_by_user
    WHERE id_tv_show_created_by_user = old.id_tv_show_created_by_user;
  END IF;
END//

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
DROP TRIGGER delete_user_tv_show;
DROP TRIGGER bi_tv_show_reminder_check_nulls_in_fks;
DROP TRIGGER bu_tv_show_reminder_check_nulls_in_fks;
show triggers;
#############
