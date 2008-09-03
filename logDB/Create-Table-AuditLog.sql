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
/

QUIT
