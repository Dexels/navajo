#!/bin/bash
#
# shell runner for studio that works for Cygwin
#
# NOTE: that while Cygwin travels around the filesystem
# using Unix style paths, the CLASSPATH variable must
# use DOS/Win style paths for the Java VM including
# semi-colon separators
#
# $Id$
#

# project home, cygwin style
PROJECTHOME=D:/Projecten/Navajo

# required libraries, edit accordingly
NAVAJOJAR="D:\\Projecten\Navajo\\Navajo.jar"
JRUNTIMEJAR="C:\\JBuilder6\\jdk1.3.1\\jre\\lib\\rt.jar"
XMLJARS="C:\\JBuilder6\\cocoon\\lib\\xerces_1_2.jar"
BORLANDJAR="C:\\JBuilder6\\lib\\jbcl.jar"

export CLASSPATH="${JRUNTIMEJAR};${NAVAJOJAR};${XMLJARS};${BORLANDJAR}"

# kludge, the images don't seem to be found in the jar
# so we change directory to the Navajo project
cd ${PROJECTHOME} && \
  java -cp ${CLASSPATH} com.dexels.navajo.studio.MainFrame

### EOF: $RCSfile$ ###
