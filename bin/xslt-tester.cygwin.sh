#
# tester for NavaDoc XSLT stylesheet against Navajo Documents
# for Cygwin bash shell
#
# $Id$
#

if [ "$#" != "1" ]; then
  echo "usage: $0 <Navajo Service Name (no extensions, please)>"
  exit 1
fi

# project home, Cygwin style
PROJECTHOME=D:Projecten/NavaDoc
TARGETDIR=doc

# script home, Java style
SCRIPTHOME=../sportlink-serv/navajo-tester/auxilary/scripts

# jav file paths, Win style
export CLASSPATH="${CLASSPATH};D:\\Projecten\\NavaDoc\\lib\\xalan_1_2_D02.jar"
export CLASSPATH="${CLASSPATH};D:\\Projecten\\NavaDoc\\lib\\xerces_1_2.jar"

for ext in tml xsl
do
  SOURCE=${SCRIPTHOME}/$1.${ext}
  TARGET=${TARGETDIR}/$1-${ext}.html
  if [ -f ${SOURCE} ]; then
      cd ${PROJECTHOME} && \
        java -cp ${CLASSPATH} org.apache.xalan.xslt.Process \
	  -IN ${SOURCE} \
	  -XSL xsl/navadoc.xsl > ${TARGET}
  else
     echo "${SOURCE} file not found" 
  fi
done

exit 0

### EOF: $RCSfile$ ###


