export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
# Alter repository:

echo "Altering Serv"

cd $WORKSPACE/com.sportlink.serv
git rm -r META-INF
git rm -r *.library
git rm -r *.jpx
git rm -r *.local
git rm -r *.pkcs8
git rm -r *.crt
git rm -r LINES_OF_CODE
git rm -r *.xsl

git rm -r *.bat
git rm -r *.cer
git rm -r *.xpr
git rm -r PERFORMANCE
git rm -r *.css
git rm -r doc
git rm -r install
git rm -r lib

git rm -r log
git rm -r default-web-app
git rm -r classes
git rm -r RELEASENOTES.html
git rm -r test
git rm -r src

git rm -r bin
git rm -r DEPENDENCIES.html
git rm -r bak
git rm -r sportlink-serv.html
git rm -r INSTALL.html
git rm -r create_install_zip.sh
git rm -r defaultroot
git rm -r build.xml

## add:
git rm -r scripts

git mv navajo-tester/auxilary/scripts .
git mv navajo-tester/auxilary/adapters .
git mv navajo-tester/auxilary/config .
git mv navajo-tester/auxilary/reports .

git mv navajo-tester/auxilary/tasks .
git mv navajo-tester/auxilary/workflows .

git mv navajo-tester/auxilary/authorization .
git mv navajo-tester/auxilary/conditions/ .
git mv navajo-tester/auxilary/templates .

git rm -r navajo-tester
git commit -m "gitrefactor"
