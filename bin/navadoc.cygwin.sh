#
# text runner for NavaDoc Test Suite
#
# $Id$
#

# enter your project home here, Unix Style
export PROJECTHOME="D:/Projecten/NavaDoc"
# enter project home here, Win Style, escaped backslashes
export PHOME="D:\\Projecten\\NavaDoc"

# Configuration location as a URI (Xerces is happier getting documents by URI)
CONFIG="-DconfigUri=file:///${PROJECTHOME}/config/navadoc.xml"

# set this if you want debugging output from the logging facility
# LOGDEBUG="-Dlog4j.debug=yes"

# class path
export CLASSPATH="${PHOME}\\classes"
cd ${PROJECTHOME}/lib
for jar in *.jar
do
  export CLASSPATH="${CLASSPATH};${PHOME}\\lib\\$jar"
done

cd ${PROJECTHOME}/classes && \
    java -cp ${CLASSPATH} ${CONFIG} ${LOGDEBUG} \
        com.dexels.navajo.util.navadoc.NavaDoc

### EOF: $RCSfile$ ###


