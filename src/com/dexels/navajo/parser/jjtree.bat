@echo off
set MBACKUP=%CLASSPATH%
set CLASSPATH=E:\projecten\javacc2.1\bin\lib\JavaCC.zip
java COM.sun.labs.jjtree.Main %1 %2 %3 %4 %5 %6 %7 %8 %9
set CLASSPATH=%MBACKUP%
