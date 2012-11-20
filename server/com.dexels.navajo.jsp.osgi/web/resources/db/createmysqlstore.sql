CREATE TABLE statistics (
    module VARCHAR(32) NOT NULL,
    value VARCHAR(32) NOT NULL,
    week INTEGER(2) NOT NULL,
    year INTEGER(4) NOT NULL,
    timestmp TIMESTAMP  NOT NULL,
    server VARCHAR(128)
);

create table navajoaccess (
access_id 	varchar(128) not null,
webservice 	varchar(128) not null,
username 	varchar(128),
threadcount 	integer,
totaltime 	integer,
parsetime 	integer,
authorisationtime integer,
requestsize 	integer,
requestencoding varchar(64),
compressedrecv 	char(1),
compressedsnd 	char(1),
ip_address 	varchar(32),
hostname 	varchar(128),
clientid        varchar(255),
clienttime      integer,
created 	timestamp,
agentid varchar(128),
clientinfo varchar(2048),
cpuload float,
parent_access_id varchar(256)


);

create table navajomap (
  access_id       varchar(128) not null,
  sequence_id     integer not null,
  level_id        integer not null,
  mapname         varchar(128) not null,
  array           char(1) default '0',
  instancecount   integer,
  totaltime       integer,
  created         timestamp not null
);

create table navajoasync (
  access_id       varchar(128) not null,
  ref_id          varchar(128) not null,
  asyncmap        varchar(128) not null,
  totaltime       integer,
  exception       blob,
  created         timestamp not null
    
);

create table navajolog (
access_id 	varchar(128) not null, 
exception 	blob, 
navajoin 	blob, 
navajoout 	blob,
console blob
);


CREATE TABLE auditlog (
   instance VARCHAR(255),
   subsystem VARCHAR(255),
   message VARCHAR(4000),
   auditlevel VARCHAR(255),
   accessid VARCHAR(255),
   lastupdate TIMESTAMP
);


CREATE TABLE propertydescription  (
   DESCRIPTIONID        INTEGER(9)                       not null,
   LOCALE               VARCHAR(32)                    not null,
   SUBLOCALE            VARCHAR(32),
   NAME                 VARCHAR(255)                   not null,
   CONTEXT              VARCHAR(255),
   OBJECTID             VARCHAR(32),
   OBJECTTYPE           VARCHAR(32),
   DESCRIPTION          VARCHAR(255),
   UPDATEBY             VARCHAR(32),                  
   LASTUPDATE           DATE 

--   constraint PROPERTYDESCRIPTION_PK primary key (DESCRIPTIONID) using index
);

