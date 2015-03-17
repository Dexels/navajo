mvn deploy:deploy-file -DrepositoryId=dexels -Durl=http://10.0.0.1:9090/nexus/content/repositories/thirdparty -DgroupId=$2 -DartifactId=$3 -Dversion=$4 -Dpackagingjar -Dfile=$1 -Dpackaging=jar
echo "mvn:com.dexels.thirdparty/$2/$3"
