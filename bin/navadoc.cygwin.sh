#
# runner for NavaDoc command line utility
# for Cygwin bash shell
#
# $Id$
#

# jav file paths, Win style
LIB_BASE="D:\\Projecten\\Navadoc"
export CLASSPATH="${LIB_BASE}\\lib\\xalan_1_2_D02.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\xerces_1_2.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\commons-logging.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\lib\\log4j-1.2.7.jar"
export CLASSPATH="${CLASSPATH};${LIB_BASE}\\classes"

# Configuration location
# CONFIG="--config D:\\Projecten\\NavaDoc\\config\\navadoc.xml"

# project home, Cygwin style
PROJECTHOME=D:Projecten/NavaDoc

cd ${PROJECTHOME}/classes &&
  java -cp ${CLASSPATH} ${LOGPARAM} com.dexels.navajo.util.navadoc.NavaDoc ${CONFIG}

### EOF: $RCSfile$ ###


