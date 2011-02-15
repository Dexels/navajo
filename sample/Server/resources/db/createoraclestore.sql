DROP TABLE STATISTICS
/

CREATE TABLE STATISTICS (
MODULE VARCHAR2(32) NOT NULL,
VALUE VARCHAR2(32) NOT NULL,
WEEK NUMBER(2) NOT NULL,
YEAR NUMBER(4) NOT NULL,
TIMESTMP TIMESTAMP(6) WITH TIME ZONE,
SERVER VARCHAR2(128)
)
pctfree 20
pctused 40
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace XXXKERN
/

CREATE INDEX STATISTICS_MODL_SRV_TIME ON statistics
(
  MODULE, SERVER, TIMESTMP
)
pctfree 20
storage
(
    initial 1024K
    next 1024K
    pctincrease 0
)
tablespace XXXINDX
/

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
       tablespace XXXINDX
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
tablespace XXXKERN
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
       tablespace XXXINDX
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
tablespace XXXKERN
/

DROP TABLE NAVAJOASYNC 
/

CREATE TABLE NAVAJOASYNC (
  ACCESS_ID       VARCHAR2(128) NOT NULL,
  REF_ID          VARCHAR2(128) NOT NULL,
  ASYNCMAP        VARCHAR2(128) NOT NULL,
  TOTALTIME       NUMBER,
  EXCEPTION       BLOB,
  CREATED         TIMESTAMP WITH TIME ZONE DEFAULT SYSTIMESTAMP NOT NULL,
  CONSTRAINT NAVAJOASYNC_PK primary key (ACCESS_ID, REF_ID) using index
      pctfree 10
       tablespace XXXINDX
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
tablespace XXXKERN
/

DROP TABLE NAVAJOLOG
/

CREATE TABLE NAVAJOLOG (
ACCESS_ID 	VARCHAR2(128) NOT NULL, 
EXCEPTION 	BLOB, 
NAVAJOIN 	BLOB, 
NAVAJOOUT 	BLOB,
   CONSTRAINT NAVAJOLOG_PK primary key (ACCESS_ID)
                using index
      pctfree 10
       tablespace XXXINDX
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
tablespace XXXKERN
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
tablespace XXXINDX
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
tablespace XXXINDX
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
tablespace XXXINDX
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
tablespace XXXKERN
/

DROP TABLE PROPERTYDESCRIPTION
/

CREATE TABLE PROPERTYDESCRIPTION  (
   DESCRIPTIONID        NUMBER(9)                       not null,
   LOCALE               VARCHAR2(32)                    not null,
   SUBLOCALE            VARCHAR2(32),
   NAME                 VARCHAR2(255)                   not null,
   CONTEXT              VARCHAR2(255),
   OBJECTID             VARCHAR2(32),
   OBJECTTYPE           VARCHAR2(32),
   DESCRIPTION          VARCHAR2(255),
   UPDATEBY             VARCHAR2(32)                   default USER not null,
   LASTUPDATE           DATE                           default SYSDATE not null,
   constraint PROPERTYDESCRIPTION_PK primary key (DESCRIPTIONID)
         using index
       pctfree 10
       tablespace XXXINDX
       storage
       (
           initial 64K
           next 64K
           pctincrease 0
       )
)
pctfree 20
pctused 40
storage
(
    initial 64K
    next 64K
    pctincrease 0
)
tablespace XXXKERN;

comment on table PROPERTYDESCRIPTION is
'Table for defining property descriptions using locales';

comment on column PROPERTYDESCRIPTION.DESCRIPTIONID is
'A unique, sequenced identifier for the property description';

comment on column PROPERTYDESCRIPTION.LOCALE is
'The locale for the property description';

comment on column PROPERTYDESCRIPTION.SUBLOCALE is
'SubLocale';

comment on column PROPERTYDESCRIPTION.NAME is
'The name of a property';

comment on column PROPERTYDESCRIPTION.CONTEXT is
'Context';

comment on column PROPERTYDESCRIPTION.OBJECTID is
'The relation code of a person or club to whom the property applies';

comment on column PROPERTYDESCRIPTION.OBJECTTYPE is
'The type of object for the objectid';

comment on column PROPERTYDESCRIPTION.DESCRIPTION is
'A description for a property';

comment on column PROPERTYDESCRIPTION.UPDATEBY is
'User identifier for the person that last updated the row';

comment on column PROPERTYDESCRIPTION.LASTUPDATE is
'last time the row was updated';
/

create sequence propertydescription_seq
/

SHOW ERRORS
QUIT
