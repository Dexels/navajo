@echo off
set MBACKUP=%CLASSPATH%
set CLASSPATH=c:\projecten\javacc\bin\lib\JavaCC.jar
java COM.sun.labs.javacc.Main %1 %2 %3 %4 %5 %6 %7 %8 %9
set CLASSPATH=%MBACKUP%
