CREATE INDEX PROPDESC_INDEX ON PROPERTYDESCRIPTION (
  NAME
, LOCALE 
, NVL(SUBLOCALE,'%')
, NVL(OBJECTID, '%')
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

QUIT
