# Alter repository:

echo "Altering Serv"

cd $WORKSPACE/com.sportlink.serv
hg remove META-INF
hg remove *.library
hg remove *.jpx
hg remove *.local
hg remove *.pkcs8
hg remove *.crt
hg remove LINES_OF_CODE
hg remove *.xsl

hg remove *.bat
hg remove *.cer
hg remove *.xpr
hg remove PERFORMANCE
hg remove *.css
hg remove doc
hg remove install
hg remove lib

hg remove log
hg remove default-web-app
hg remove classes
hg remove RELEASENOTES.html
hg remove test
hg remove src

hg remove bin
hg remove DEPENDENCIES.html
hg remove bak
hg remove sportlink-serv.html
hg remove INSTALL.html
hg remove create_install_zip.sh
hg remove defaultroot
hg remove build.xml

hg mv navajo-tester/auxilary/scripts .
hg mv navajo-tester/auxilary/adapters .
hg mv navajo-tester/auxilary/config .
hg mv navajo-tester/auxilary/reports .


hg mv navajo-tester/auxilary/authorization .
hg mv navajo-tester/auxilary/conditions/ErrorCodes.dat conditions/ErrorCodes.dat .
hg mv navajo-tester/auxilary/templates .

hg remove navajo-tester
