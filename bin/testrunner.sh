#
# text runner for NavaDoc Test Suite
#
# $Id$
#

# enter your project home here
export PROJECTHOME="${HOME}/projects/NavaDoc"

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file://${PROJECTHOME}/test/config/navadoc.xml"

# test data path
TESTDATA="-Dtestdata-path=${PROJECTHOME}/test/data"

# define save property if you want to keep the results of the test
# SAVE="-DsaveResults=yes"

# set this if you want debugging output from the logging facility
# LOGDEBUG="-Dlog4j.debug=yes"

cd ${PROJECTHOME} && \
    java -cp NavaDoc.jar ${CONFIG} ${TESTDATA} ${SAVE} ${LOGDEBUG} \
        junit.textui.TestRunner com.dexels.navajo.util.navadoc.NavaDocTestSuite

### EOF: $RCSfile$ ###


