#
# text runner for NavaDoc Test Suite
#
# $Id$
#

# enter your project home here, Unix Style
export PROJECTHOME="D:/Projecten/NavaDoc"

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file:///${PROJECTHOME}/config/navadoc.xml"

# set this if you want debugging output from the logging facility
# LOGDEBUG="-Dlog4j.debug=yes"

cd ${PROJECTHOME} && \
    java -cp NavaDoc.jar ${CONFIG} ${LOGDEBUG} \
        com.dexels.navajo.util.navadoc.NavaDoc

### EOF: $RCSfile$ ###


