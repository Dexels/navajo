#
# tester for NavaDoc XSLT stylesheet against Navajo Documents
# for Cygwin bash shell
#
# $Id$
#

# project home, Cygwin style
PROJECTHOME=D:Projecten/NavaDoc

# script home, Java style
SCRIPTHOME=../sportlink-serv/navajo-tester/auxilary/scripts

# jav file paths, Win style
export CLASSPATH="${CLASSPATH};D:\\Projecten\\NavaDoc\\lib\\xalan_1_2_D02.jar"
export CLASSPATH="${CLASSPATH};D:\\Projecten\\NavaDoc\\lib\\xerces_1_2.jar"

cd ${PROJECTHOME} && \
  java -cp ${CLASSPATH} org.apache.xalan.xslt.Process \
    -IN  ${SCRIPTHOME}/$1 \
    -XSL xsl/navadoc.xsl

### EOF: $RCSfile$ ###


