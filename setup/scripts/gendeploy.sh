mvn deploy:deploy-file -DrepositoryId=dexels -Durl=http://repo.dexels.com/repository/thirdparty/ -DgroupId=$2 -DartifactId=$3 -Dversion=$4 -Dpackagingjar -Dfile=$1 -Dpackaging=jar
echo "mvn:com.dexels.thirdparty/$2/$3"
