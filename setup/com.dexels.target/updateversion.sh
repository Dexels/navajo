#!/bin/sh
VERSION=$1
sed "s/DEXELSBASEVERSION/${VERSION}/g" maven.template >maven.target
echo $VERSION >VERSION
