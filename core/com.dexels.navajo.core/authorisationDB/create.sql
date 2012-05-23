<<<<<<< create.sql
#!/bin/bash

#mysqladmin --user=root --password=xxxxxxxx drop vacature
#mysqladmin --user=root --password=xxxxxxxx create vacature

gcc -E - <<-END | mysql --user=root --password=xxxxxxxx authorisation
#define _email_ 32

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id   integer  NOT NULL,
  name   varchar(40),
  password   varchar(40),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'users.dat' INTO TABLE user;

DROP TABLE IF EXISTS service;
CREATE TABLE service (
  id integer NOT NULL,
  name varchar(100),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'services.dat' INTO TABLE service;

DROP TABLE IF EXISTS authorisation;
CREATE TABLE authorisation (
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  PRIMARY KEY (user_id, service_id) 
);
LOAD DATA LOCAL INFILE 'authorisation.dat' INTO TABLE authorisation;

DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id integer NOT NULL AUTO_INCREMENT,
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  access_id integer NOT NULL,
  level integer,
  comment varchar(100),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS timing;
CREATE TABLE timing (
  id integer NOT NULL AUTO_INCREMENT,
  access_id integer NOT NULL,
  part integer NOT NULL,
  timespent integer NOT NULL,
  PRIMARY KEY(id) 
);

DROP TABLE IF EXISTS polis_count;
CREATE TABLE polis_count(
  count integer NOT NULL,
  PRIMARY KEY (count)
);
INSERT INTO polis_count values(1000);

DROP TABLE IF EXISTS access;
CREATE TABLE access (
  id integer NOT NULL,
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  entered date, 
  tijdstip time,
  address varchar(100) NOT NULL DEFAULT "unknown",
  host varchar(255) NOT NULL DEFAULT "unknown",
  user_agent varchar(255) NOT NULL DEFAULT "unknown",
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS access_count;
CREATE TABLE access_count( 
  count integer NOT NULL,
  PRIMARY KEY (count)
);
INSERT INTO access_count values (0);

DROP TABLE IF EXISTS log_level;
CREATE TABLE log_level (
  id integer NOT NULL,
  name varchar(100),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'log_level.dat' INTO TABLE log_level;

DROP TABLE IF EXISTS parameters;
CREATE TABLE parameters(
  id integer NOT NULL AUTO_INCREMENT, 
  parameter_id integer NOT NULL,
  user_id integer NOT NULL,
  value varchar(255),
  condition varchar(255),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'parameters.dat' INTO TABLE parameters;

DROP TABLE IF EXISTS definitions;
CREATE TABLE definitions(
  parameter_id integer NOT NULL AUTO_INCREMENT,
  name varchar(100),
  type varchar(100),
  PRIMARY KEY (parameter_id)
);
LOAD DATA LOCAL INFILE 'definitions.dat' INTO TABLE definitions;

DROP TABLE IF EXISTS conditions;
CREATE TABLE conditions(
  id INTEGER NOT NULL AUTO_INCREMENT,
  service_id integer NOT NULL,
  user_id integer NOT NULL,
  condition varchar(255) NOT NULL,
  comment varchar(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS permission_defs;
CREATE TABLE permission_defs(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY (name)
);
LOAD DATA LOCAL INFILE 'permission_defs.dat' INTO TABLE permission_defs;

DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions(
  id INTEGER NOT NULL AUTO_INCREMENT,
  user_id integer NOT NULL,
  permission_id integer NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dispatch;
CREATE TABLE dispatch(
  service varchar(40), NOT NULL
  servlet varchar(40), NOT NULL
  PRIMARY KEY (service)
);             

LOAD DATA LOCAL INFILE 'dispatch.dat' INTO TABLE dispatch;                
=======
#!/bin/bash

#mysqladmin --user=root --password=xxxxxxxx drop vacature
#mysqladmin --user=root --password=xxxxxxxx create vacature

gcc -E - <<-END | mysql --user=root --password=xxxxxxxx authorisation
#define _email_ 32

DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id   integer  NOT NULL,
  name   varchar(40),
  password   varchar(40),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'users.dat' INTO TABLE users;

DROP TABLE IF EXISTS services;
CREATE TABLE services (
  id integer NOT NULL,
  name varchar(100),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'services.dat' INTO TABLE services;

DROP TABLE IF EXISTS authorisation;
CREATE TABLE authorisation (
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  PRIMARY KEY (user_id, service_id) 
);
LOAD DATA LOCAL INFILE 'authorisation.dat' INTO TABLE authorisation;

DROP TABLE IF EXISTS log;
CREATE TABLE log (
  id integer NOT NULL AUTO_INCREMENT,
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  access_id integer NOT NULL,
  log_level integer,
  comment varchar(100),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS timing;
CREATE TABLE timing (
  id integer NOT NULL AUTO_INCREMENT,
  access_id integer NOT NULL,
  part integer NOT NULL,
  timespent integer NOT NULL,
  PRIMARY KEY(id) 
);

DROP TABLE IF EXISTS polis_count;
CREATE TABLE polis_count(
  a_count integer NOT NULL,
  PRIMARY KEY (a_count)
);
INSERT INTO polis_count values(1000);

DROP TABLE IF EXISTS access;
CREATE TABLE access (
  id integer NOT NULL,
  user_id integer NOT NULL,
  service_id integer NOT NULL,
  entered date, 
  tijdstip time,
  address varchar(100) NOT NULL DEFAULT "unknown",
  host varchar(255) NOT NULL DEFAULT "unknown",
  user_agent varchar(255) NOT NULL DEFAULT "unknown",
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS access_count;
CREATE TABLE access_count( 
  a_count integer NOT NULL,
  PRIMARY KEY (a_count)
);
INSERT INTO access_count values (0);

DROP TABLE IF EXISTS log_level;
CREATE TABLE log_level (
  id integer NOT NULL,
  name varchar(100),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'log_level.dat' INTO TABLE log_level;

DROP TABLE IF EXISTS parameters;
CREATE TABLE parameters(
  id integer NOT NULL AUTO_INCREMENT, 
  parameter_id integer NOT NULL,
  user_id integer NOT NULL,
  value varchar(255),
  condition varchar(255),
  PRIMARY KEY (id)
);
LOAD DATA LOCAL INFILE 'parameters.dat' INTO TABLE parameters;

DROP TABLE IF EXISTS definitions;
CREATE TABLE definitions(
  parameter_id integer NOT NULL AUTO_INCREMENT,
  name varchar(100),
  type varchar(100),
  PRIMARY KEY (parameter_id)
);
LOAD DATA LOCAL INFILE 'definitions.dat' INTO TABLE definitions;

DROP TABLE IF EXISTS conditions;
CREATE TABLE conditions(
  id INTEGER NOT NULL AUTO_INCREMENT,
  service_id integer NOT NULL,
  user_id integer NOT NULL,
  condition varchar(255) NOT NULL,
  comment varchar(255),
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS permission_defs;
CREATE TABLE permission_defs(
  id INTEGER NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id),
  KEY (name)
);
LOAD DATA LOCAL INFILE 'permission_defs.dat' INTO TABLE permission_defs;

DROP TABLE IF EXISTS permissions;
CREATE TABLE permissions(
  id INTEGER NOT NULL AUTO_INCREMENT,
  user_id integer NOT NULL,
  permission_id integer NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS dispatch;
CREATE TABLE dispatch(
  service varchar(40) NOT NULL,
  servlet varchar(40) NOT NULL,
  PRIMARY KEY (service)
);             

LOAD DATA LOCAL INFILE 'dispatch.dat' INTO TABLE dispatch;                
>>>>>>> 1.3
