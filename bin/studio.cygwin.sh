#
# bash shell runner for Navajo Studio which works for Cygwin
# check the first variables and adjust as necessary!
# no warranties guaranteed or otherwise implied
# all rights reserved ;^) meichler@dexels.com
#
# $Id$
#

NAVAJOJAR="D:\\Projecten\\Navajo\\Navajo.jar"
JRUNTIMEJAR="C:\\JBuilder6\\jdk1.3.1\\jre\\lib\\rt.jar"
XMLJARS="C:\\JBuilder6\\cocoon\lib\xerces_1_2.jar;C:\\JBuilder6\\extras\\BorlandXML\\lib\\jaxp-patch.jar"
BORLANDJAR="C:\\JBuilder6\\lib\\jbcl.jar"

export CLASSPATH="${JRUNTIMEJAR};${XMLJARS};${BORLANDJAR};${NAVAJOJAR}"

# kludge, we can't seem to find images in the jar
cd D:Projecten/Navajo
java -cp ${CLASSPATH} com.dexels.navajo.studio.MainFrame

### EOF: $RCSfile$ ###



