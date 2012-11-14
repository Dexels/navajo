SET SERVEROUTPUT ON

CREATE OR REPLACE PROCEDURE sp_processcleanupnavajolog(
   deletecount	OUT	NUMBER
)
AS
   CURSOR accessid_cur
   IS SELECT access_id
      FROM   navajoaccess
      WHERE  TRUNC( created ) < TRUNC( SYSDATE - 7 );
   
   CURSOR asynclog_cur
   IS SELECT access_id, ref_id
      FROM   navajoasync
      WHERE  TRUNC( created ) < TRUNC( SYSDATE - 7 );

   TYPE navajoasync_rec IS RECORD (
      access_id VARCHAR2(128),
      ref_id    VARCHAR2(128)
   );

   TYPE accessidlist_type IS TABLE OF navajoaccess.access_id%TYPE;
   
   TYPE navajoasynclist_type IS TABLE OF navajoasync_rec;

   accessid_list   accessidlist_type;
   
   navajoasync_list navajoasynclist_type;

   l_count   NUMBER    := 0;
   i         NUMBER    := 0;

BEGIN
   dbms_output.enable( 2000000 );

   OPEN accessid_cur;

   FETCH accessid_cur
   BULK COLLECT INTO accessid_list;

   CLOSE accessid_cur;

   OPEN asynclog_cur;
   FETCH asynclog_cur
   BULK COLLECT INTO navajoasync_list;
   CLOSE asynclog_cur;

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

   FOR i IN navajoasync_list.FIRST .. navajoasync_list.LAST LOOP

     DELETE FROM navajoasync
     WHERE       access_id = navajoasync_list(i).access_id AND ref_id = navajoasync_list(i).ref_id
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

   -- empty the auditlog table
   DELETE FROM auditlog WHERE lastupdate < SYSDATE - 7;

   -- empty worklow event table
   DELETE from navajoworkflow where eventtime < sysdate - 7;

   deletecount := l_count;

END sp_processcleanupnavajolog;
/

SHOW ERRORS
QUIT

