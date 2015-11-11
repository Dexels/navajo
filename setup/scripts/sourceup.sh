#!/bin/bash
groupId=$2
current=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
file=$1
strip=`echo $file | rev | cut -c 5- | rev`
echo "Before: $strip"
artifactId="${strip%-*}"
version="${strip##*-}"
echo "ArtifactId: $artifactId"
echo "Version: $version"
mvn deploy:deploy-file -DrepositoryId=dexels_source -Durl=http://10.0.0.1:9090/nexus/content/repositories/thirdparty -DgroupId=$groupId -DartifactId=$artifactId -Dversion=$version -Dfile=$1 -Dpackaging=jar


