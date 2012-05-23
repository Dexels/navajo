USE navajostore;

CREATE TABLE statistics (
    module VARCHAR(32) ,
    value VARCHAR(32) ,
    week INTEGER(2) ,
    year INTEGER(4) ,
    timestmp TIMESTAMP  ,
    server VARCHAR(128)
);

CREATE INDEX STATISTICS_MODL_SRV_TIME ON statistics
(
  module,server, timestmp
);

CREATE TABLE navajoaccess (
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
agentid         varchar(128),
clientinfo      varchar(2048),
cpuload         float,
parent_access_id varchar(255),
queuetime       integer,
processingtime  integer,
queueid         varchar(255),
queuelength     integer,
PRIMARY KEY (access_id)
);

CREATE TABLE
    NAVAJOWORKFLOW
    (
        NAME VARCHAR2(256) NOT NULL,
        ID VARCHAR2(256) NOT NULL,
        EVENTTIME DATE NOT NULL,
        SEQ NUMBER,
        EVENTTYPE VARCHAR2(256) NOT NULL,
        CURRENTSTATE VARCHAR2(256) NOT NULL,
        NEXTSTATE VARCHAR2(256),
        TRIGGERNAME VARCHAR2(1024),
        ACCESS_ID VARCHAR2(128),
        WEBSERVICE VARCHAR2(256),
        STATEDUMP BLOB
    )
    
CREATE TABLE navajomap (
  access_id       varchar(128) not null,
  sequence_id     integer not null,
  level_id        integer not null,
  mapname         varchar(128) not null,
  array           char(1) default '0',
  instancecount   integer,
  totaltime       integer,
  created         timestamp not null,
  PRIMARY KEY (access_id, sequence_id, level_id)
);

create table navajoasync (
  access_id       varchar(128) not null,
  ref_id          varchar(128) not null,
  asyncmap        varchar(128) not null,
  totaltime       integer,
  exception       blob,
  created         timestamp not null,
  PRIMARY KEY (access_id)
);

create table navajolog (
access_id 	varchar(128) not null, 
exception 	blob, 
navajoin 	blob, 
navajoout 	blob,
console         blob,
PRIMARY KEY (access_id)
);

create index navajoaccess_indx on navajoaccess (
  username,
  webservice
);

CREATE INDEX NAVAJOACCCESS_INDX_WS ON navajoaccess (
  WEBSERVICE
);
CREATE INDEX NAVAJOACCCESS_INDX_US ON navajoaccess (
  USERNAME
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
   DESCRIPTIONID        INTEGER AUTO_INCREMENT NOT NULL,
   LOCALE               VARCHAR(32),                    
   SUBLOCALE            VARCHAR(32),
   NAME                 VARCHAR(255),
   CONTEXT              VARCHAR(255),
   OBJECTID             VARCHAR(32),
   OBJECTTYPE           VARCHAR(32),
   DESCRIPTION          VARCHAR(255),
   UPDATEBY             VARCHAR(32),                   
   LASTUPDATE           DATE,                          
   PRIMARY KEY (DESCRIPTIONID)
);

CREATE INDEX PROPDESC_INDEX ON propertydescription (
  OBJECTID
, NAME
, LOCALE
);

CREATE TABLE navajoaccess_agent (
        created date, 
        agent varchar(1024),
        calls integer,
        totaltime integer,
        avgtime integer,
        stdevtime integer,
        maxtime integer,
        mintime integer,
        errors integer,
        internal integer
);

CREATE TABLE navajoaccess_consol (
        created date,
        calls integer,
        totaltime integer,
        internal integer
);

CREATE TABLE navajoaccess_user (
        created DATE,
        username VARCHAR(256),
        calls integer,
        totaltime integer,
        avgtime integer,
        stdevtime integer,
        maxtime integer,
        mintime integer,
        errors integer,
        internal integer
);

CREATE TABLE navajoaccess_user_service (
        created date, 
        agent varchar(256),
        username varchar(256),
        webservice varchar(1024),
        calls integer,
        totaltime integer,
        avgtime integer,
        stdevtime integer,
        maxtime integer,
        mintime integer,
        errors integer,
        internal integer
);

CREATE TABLE navajoaccess_webservice (
        created date,
        webservice varchar(1024),
        calls integer,
        totaltime integer,
        avgtime integer,
        stdevtime integer,
        maxtime integer,
        mintime integer,
        errors integer,
        internal integer
);
