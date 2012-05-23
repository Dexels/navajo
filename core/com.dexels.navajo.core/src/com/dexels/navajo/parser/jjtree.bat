@echo off
set MBACKUP=%CLASSPATH%
set CLASSPATH=C:\projecten\javacc\bin\lib\JavaCC.jar
java jjtree %1 %2 %3 %4 %5 %6 %7 %8 %9
set CLASSPATH=%MBACKUP%
