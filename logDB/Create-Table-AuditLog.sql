CREATE TABLE auditlog (
   instance VARCHAR2(255),
   subsystem VARCHAR2(255),
   message VARCHAR2(255),
   auditlevel VARCHAR2(255),
   lastupdate DATE DEFAULT sysdate
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
