mvn deploy:deploy-file -DrepositoryId=dexels -Durl=http://repo.dexels.com/nexus/content/repositories/thirdparty -DgroupId=com.dexels.thirdparty -DartifactId=$2 -Dversion=$3 -Dpackagingjar -Dfile=$1 -Dpackaging=jar
echo "mvn:com.dexels.thirdparty/$2/$3"
