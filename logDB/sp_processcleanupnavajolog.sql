/*==============================================================*/
/* Database name:  sportlink22					*/
/* DBMS name:	   ORACLE Version 9i				*/
/* Created on:	   2004/03/12					*/
/* Description:	   Cleans up navajolog (everything older than 1 week) */
/*		   DOES NOT INCLUDE KNVB-DISTRICT-BETAALD	*/
/*==============================================================*/

--
-- $Id$
--

SET SERVEROUTPUT ON

CREATE OR REPLACE PROCEDURE sp_processcleanupnavajolog(
   deletecount	OUT	NUMBER
)
AS
   CURSOR accessid_cur
   IS SELECT access_id
      FROM   navajoaccess
      WHERE  TRUNC( created ) < TRUNC( SYSDATE - 7 )
      ;

   TYPE accessidlist_type IS TABLE OF navajoaccess.access_id%TYPE;

   accessid_list   accessidlist_type;

   l_count   NUMBER    := 0;
   i         NUMBER    := 0;

BEGIN
   dbms_output.enable( 2000000 );

   OPEN accessid_cur;

   FETCH accessid_cur
   BULK COLLECT INTO accessid_list;

   CLOSE accessid_cur;

   IF ( accessid_list.FIRST IS NULL ) THEN
     deletecount := 0;
     RETURN;
   END IF;

   -- empty the navajomap table
   l_count := 0;

   FOR i IN accessid_list.FIRST .. accessid_list.LAST LOOP

     DELETE FROM navajomap
     WHERE       access_id = accessid_list(i)
     ;

     l_count := l_count + 1;

     IF ( ( l_count MOD 1000 ) = 0 ) THEN
       COMMIT;
     END IF;

   END LOOP;

   COMMIT;

   -- empty the navajolog table
   l_count := 0;

   FOR i IN accessid_list.FIRST .. accessid_list.LAST LOOP

     DELETE FROM navajolog
     WHERE       access_id = accessid_list(i)
     ;

     l_count := l_count + 1;

     IF ( ( l_count MOD 2000 ) = 0 ) THEN
       COMMIT;
     END IF;

   END LOOP;

   COMMIT;

   -- empty the navajoasync table
   l_count := 0;

   FOR i IN accessid_list.FIRST .. accessid_list.LAST LOOP

     DELETE FROM navajoasync
     WHERE       access_id = accessid_list(i)
     ;

     l_count := l_count + 1;

     IF ( ( l_count MOD 1000 ) = 0 ) THEN
       COMMIT;
     END IF;

   END LOOP;

   COMMIT;

   -- empty the navajoaccess table
   l_count := 0;

   FOR i IN accessid_list.FIRST .. accessid_list.LAST LOOP

     DELETE FROM navajoaccess
     WHERE       access_id = accessid_list(i)
     ;

     l_count := l_count + 1;

     IF ( ( l_count MOD 2000 ) = 0 ) THEN
       COMMIT;
     END IF;

   END LOOP;

   COMMIT;

   deletecount := l_count;

END sp_processcleanupnavajolog;
/

SHOW ERRORS
QUIT

