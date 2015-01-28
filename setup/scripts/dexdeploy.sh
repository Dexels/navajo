mvn deploy:deploy-file -DrepositoryId=dexels -Durl=https://repo.dexels.com/nexus/content/repositories/thirdparty -DgroupId=com.dexels.repository -DartifactId=$2 -Dversion=$3 -Dpackagingjar -Dfile=$1 -Dpackaging=jar
echo "mvn:com.dexels.repository/$2/$3"
