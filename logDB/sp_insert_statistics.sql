/*==============================================================*/
/* Database name:  sportlink                                    */
/* DBMS name:      ORACLE Version 9i                            */
/* Created on:     07-4-2009 10:33:50                           */
/*==============================================================*/


--
-- $Id$
--

CREATE OR REPLACE PROCEDURE sp_insert_statistics ( in_server VARCHAR2, in_threadcount NUMBER, in_serverload NUMBER )
IS

  l_versionid VARCHAR2(128) := '$Id$';
  l_sysdate     TIMESTAMP;
BEGIN

  l_sysdate := sysdate;

  DELETE FROM statistics WHERE module IN ('THREADCOUNT', 'SERVERLOAD', 'CPULOAD' ) AND timestmp < sysdate - 1;
  INSERT INTO statistics (module, value, week, year, timestmp, server ) VALUES ( 'THREADCOUNT', in_threadcount, to_char(l_sysdate, 'IW'), to_char(l_sysdate, 'YYYY'), l_sysdate, in_server );
  INSERT INTO statistics (module, value, week, year, timestmp, server ) VALUES ( 'SERVERLOAD', in_serverload, to_char(l_sysdate, 'IW'), to_char(l_sysdate, 'YYYY'), l_sysdate, in_server );
  --INSERT INTO statistics (module, value, week, year, timestmp, server ) VALUES ( 'CPULOAD', in_cpuload, to_char(l_sysdate, 'IW'), to_char(l_sysdate, 'YYYY'), l_sysdate, in_server );

END sp_insert_statistics;
/


GRANT EXECUTE ON sp_insert_statistics
  TO PUBLIC
/
SHOW ERRORS
QUIT


--
-- EOF: $RCSfile$
--
