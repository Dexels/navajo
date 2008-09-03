DROP TABLE NAVAJOACCESS
/

CREATE TABLE NAVAJOACCESS (
ACCESS_ID 	VARCHAR2(128) NOT NULL,
WEBSERVICE 	VARCHAR2(128) NOT NULL,
USERNAME 	VARCHAR2(128),
THREADCOUNT 	NUMBER,
TOTALTIME 	NUMBER,
PARSETIME 	NUMBER,
AUTHORISATIONTIME NUMBER,
REQUESTSIZE 	NUMBER,
REQUESTENCODING VARCHAR2(64),
COMPRESSEDRECV 	CHAR(1),
COMPRESSEDSND 	CHAR(1),
IP_ADDRESS 	VARCHAR2(32),
HOSTNAME 	VARCHAR2(128),
CLIENTID        VARCHAR2(255),
CLIENTTIME      NUMBER,
CREATED 	TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
	CONSTRAINT NAVAJOACCESS_PK primary key (ACCESS_ID) 
		using index
pctfree 10
       tablespace USERS
       storage
       (
           initial 1024K
           next 1024K
           pctincrease 0
       )
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP TABLE NAVAJOMAP
/

CREATE TABLE NAVAJOMAP (
  ACCESS_ID       VARCHAR2(128) NOT NULL,
  SEQUENCE_ID     NUMBER NOT NULL,
  LEVEL_ID        NUMBER NOT NULL,
  MAPNAME         VARCHAR(128) NOT NULL,
  ARRAY           CHAR(1) DEFAULT '0',
  INSTANCECOUNT   NUMBER,
  TOTALTIME       NUMBER,
  CREATED         TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
  CONSTRAINT NAVAJOMAP_PK primary key (ACCESS_ID, SEQUENCE_ID, LEVEL_ID) using index
    pctfree 10
       tablespace USERS
       storage
       (
           initial 1024K
           next 1024K
           pctincrease 0
       )
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP TABLE NAVAJOASYNC 
/

CREATE TABLE NAVAJOASYNC (
  ACCESS_ID       VARCHAR2(128) NOT NULL,
  REF_ID          VARCHAR2(128) NOT NULL,
  ASYNCMAP        VARCHAR2(128) NOT NULL,
  TOTALTIME       NUMBER,
  EXCEPTION       VARCHAR2(4000),
  CREATED         TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
  CONSTRAINT NAVAJOASYNC_PK primary key (ACCESS_ID, REF_ID) using index
      pctfree 10
       tablespace USERS
       storage
       (
           initial 1024K
           next 1024K
           pctincrease 0
       )
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP TABLE NAVAJOLOG
/

CREATE TABLE NAVAJOLOG (
ACCESS_ID 	VARCHAR2(128) NOT NULL, 
EXCEPTION 	VARCHAR2(4000), 
NAVAJOIN 	BLOB, 
NAVAJOOUT 	BLOB,
   CONSTRAINT NAVAJOLOG_PK primary key (ACCESS_ID)
                using index
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP INDEX navajoaccess_indx
/

CREATE INDEX navajoaccess_indx ON navajoaccess (
  username,
  webservice
)
pctfree 20
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP INDEX NAVAJOACCCESS_INDX_WS
/

CREATE INDEX NAVAJOACCCESS_INDX_WS ON navajoaccess (
  WEBSERVICE
)
pctfree 20
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP INDEX NAVAJOACCCESS_INDX_US
/

CREATE INDEX NAVAJOACCCESS_INDX_US ON navajoaccess (
  USERNAME
)
pctfree 20
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS
/

DROP TABLE auditlog
/

CREATE TABLE auditlog (
   instance VARCHAR2(255),
   subsystem VARCHAR2(255),
   message VARCHAR2(4000),
   auditlevel VARCHAR2(255),
   accessid VARCHAR2(255),
   lastupdate TIMESTAMP(6) WITH TIME ZONE DEFAULT sysdate
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace USERS

QUIT
