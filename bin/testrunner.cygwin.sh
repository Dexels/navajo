#
# runner for the Swing UI test runner
# for Cygwin bash shell
#
# $Id$
#

# jav file paths, Win style
LIB_BASE="D:\\Projecten\\Navadoc"

# JUnit
JUNIT="C:\\JBuilder6\extras\junit\\junit.jar"

export CLASSPATH="${LIB_BASE}\\lib\\xalan_1_2_D02.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\xerces_1_2.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\commons-logging.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\log4j-1.2.7.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\classes"
export CLASSPATH="${CLASSPATH};${JUNIT}"

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file:///d:/Projecten/NavaDoc/config/navadoc.xml"

# project home, Cygwin style
PROJECTHOME=D:Projecten/NavaDoc

# cd ${PROJECTHOME}/classes &&
java -cp ${CLASSPATH} junit.swingui.TestRunner

### EOF: $RCSfile$ ###


