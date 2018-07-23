#!/bin/bash

groupId=dexels.repository
urlGroupId='dexels/repository'
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


#Check if this new version already exists
STATUS=`curl  -s -w "%{http_code}" "http://repo.dexels.com/repository/dexelsrepo/$urlGroupId/$artifactId/$version/$artifactId-$version.jar" -o /dev/null`
if [ $STATUS -eq "200" ]
then
    echo "$artifactId version $version already exists - exiting!"
    exit 1
fi

#mvn deploy:deploy-file -Durl=file://${current}/repo/ -Dfile=$1 -DgroupId=$groupId -DartifactId=$artifactId -Dpackaging=jar -Dversion=$version
mvn deploy:deploy-file -DrepositoryId=dexels -Durl=http://repo.dexels.com/repository/dexelsrepo -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Dfile=$1 -Dpackaging=jar


