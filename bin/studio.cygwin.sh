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

NAVAJOHOME="D:\\Projecten\\Navajo"
BORLANDHOME="C:\\JBuilder6"

NAVAJOJAR="${NAVAJOHOME}\\Navajo.jar;${NAVAJOHOME}\\lib\\Grus.jar"
JRUNTIMEJAR="${BORLANDHOME}\\jdk1.3.1\\jre\\lib\\rt.jar"

XMLJARS="${BORLANDHOME}\\cocoon\\lib\\xerces_1_2.jar;"
XMLJARS="${XMLJARS};${BORLANDHOME}\\extras\\BorlandXML\\lib\\jaxp-patch.jar"
XMLJARS="${XMLJARS};${NAVAJOHOME}\\lib\\jaxm-api.jar"

REGEXJAR="${NAVAJOHOME}\\lib\\gnu-regexp-1.0.8.jar"
BORLANDJAR="${BORLANDHOME}\\lib\\jbcl.jar"

export CLASSPATH="${JRUNTIMEJAR};${REGEXJAR};${NAVAJOJAR};${XMLJARS};${BORLANDJAR}"

# kludge, the images don't seem to be found in the jar
# so we change directory to the Navajo project
cd ${PROJECTHOME} && \
  java -cp ${CLASSPATH} com.dexels.navajo.studio.MainFrame

### EOF: $RCSfile$ ###
