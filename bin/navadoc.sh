#
# text runner for NavaDoc Test Suite
#
# $Id$
#

# enter your project home here
export PROJECTHOME="/home/meichler/projects/NavaDoc"

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file://${PROJECTHOME}/config/navadoc.xml"

# set this if you want debugging output from the logging facility
# LOGDEBUG="-Dlog4j.debug=yes"

# class path
export CLASSPATH
for jar in ${PROJECTHOME}/lib/*.jar
do
  export CLASSPATH="${CLASSPATH}:$jar"
done
export CLASSPATH="${CLASSPATH}:${PROJECTHOME}/classes"

java -cp ${CLASSPATH} ${CONFIG} ${LOGDEBUG} \
    com.dexels.navajo.util.navadoc.NavaDoc

### EOF: $RCSfile$ ###


