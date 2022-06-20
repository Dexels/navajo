#!/bin/bash
groupId=com.dexels.thirdparty
current=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
file=$1
strip=`echo $file | rev | cut -c 5- | rev`
echo "Before: $strip"
artifactWithVersion="${strip##*/}"
artifactArray=(${artifactWithVersion//-/ })
echo "ArtifactId: ${artifactArray[0]}"
echo "Version: ${artifactArray[1]}"
artifactId=${artifactArray[0]}
version=${artifactArray[1]}
#mvn deploy:deploy-file -Durl=file://${current}/repo/ -Dfile=$1 -DgroupId=$groupId -DartifactId=$artifactId -Dpackaging=jar -Dversion=$version
mvn deploy:deploy-file -DrepositoryId=dexels -Durl=https://repo.dexels.com/repository/thirdparty -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Dfile=$1 -Dpackaging=jar


