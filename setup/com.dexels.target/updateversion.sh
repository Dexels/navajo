#!/bin/sh
export TARGETBUILD=$(curl -s "https://circleci.com/api/v1.1/project/github/Dexels/dexels-base?circle-token=${CIRCLE_TOKEN}&offset=0&filter=successful" | jq '[.[]| select(.workflows.job_name == "targetplatform")][0].build_num')
export MINORVERSION=3.3
VERSION=${MINORVERSION}.${TARGETBUILD}
sed "s/DEXELSBASEVERSION/${VERSION}/g" maven.template >maven.target
echo $VERSION >VERSION
