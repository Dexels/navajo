#!/bin/bash
#
# shell runner for studio that works for Unix bash shell
#
# NOTE: that while Cygwin travels around the filesystem
# using Unix style paths, the CLASSPATH variable must
# use DOS/Win style paths for the Java VM including
# semi-colon separators
#
# $Id$
#

# project home, cygwin style
PROJECTHOME=/home/meichler/projects

# required libraries, edit accordingly

NAVAJOHOME="${PROJECTHOME}/Navajo"
BORLANDHOME="/usr/local/JBuilder6"

NAVAJOJAR="${NAVAJOHOME}/Navajo.jar:${NAVAJOHOME}/lib/Grus.jar"
JRUNTIMEJAR="${JAVA_HOME}/jre/lib/rt.jar"

XMLJARS="${NAVAJOHOME}/lib/jaxm-api.jar"
XMLJARS="${XMLJARS}:${NAVAJOHOME}/lib/xalan.jar"
XMLJARS="${XMLJARS}:${NAVAJOHOME}/lib/xerces.jar"

REGEXJAR="${NAVAJOHOME}/lib/gnu-regexp-1.0.8.jar"
BORLANDJAR="${BORLANDHOME}/lib/jbcl.jar"

# export CLASSPATH="${JRUNTIMEJAR};${REGEXJAR};${NAVAJOJAR};${XMLJARS};${BORLANDJAR}"
export CLASSPATH="${JRUNTIMEJAR}:${REGEXJAR}:${NAVAJOJAR}:${XMLJARS}:${BORLANDJAR}"

# kludge, the images don't seem to be found in the jar
# so we change directory to the Navajo project
cd ${NAVAJOHOME} && \
  java -cp ${CLASSPATH} com.dexels.navajo.studio.MainFrame

### EOF: $RCSfile$ ###
