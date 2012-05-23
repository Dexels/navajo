#!/bin/sh
#
# Text runner for NavaDoc Test Suite
#
# $Id$
#

#
# If PROJECTHOME is set use it, otherwise try to guess it
# and set NAVADOCDIR based on the PROJECTHOME
#
NAVADOCDIR=

if [ -z $PROJECTHOME ] ; then
    [ -d $HOME/Projects ]  && PROJECTHOME=$HOME/Projects
    [ -d $HOME/Projecten ] && PROJECTHOME=$HOME/Projecten
    [ -d $HOME/Project ]   && PROJECTHOME=$HOME/Project
    [ -d $HOME/projects ]  && PROJECTHOME=$HOME/projects
    [ -d $HOME/projecten ] && PROJECTHOME=$HOME/projecten
    [ -d $HOME/project ]   && PROJECTHOME=$HOME/project
    [ -d $HOME/workspace ] && PROJECTHOME=$HOME/workspace
fi

NAVADOCDIR=$PROJECTHOME/NavaDoc

if [ ! -d $NAVADOCDIR ] ; then
    echo "Could not determine NavaDoc directory; set you PROJECTHOME variable"
    exit 1
fi

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file://${NAVADOCDIR}/config/navadoc.xml -Dbase=${PROJECTHOME}"

# set this if you want debugging output from the logging facility
# LOGDEBUG="-Dlog4j.debug=yes"

cd ${NAVADOCDIR} && \
    java -cp NavaDoc.jar ${CONFIG} ${LOGDEBUG} \
        com.dexels.navajo.util.navadoc.NavaDoc

### EOF: $RCSfile$ ###
