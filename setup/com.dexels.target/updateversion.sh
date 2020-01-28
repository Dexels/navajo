#!/bin/sh
export TARGETBUILD=$(curl -s "https://circleci.com/api/v1.1/project/github/Dexels/dexels-base?circle-token=${CIRCLE_TOKEN}&offset=0&filter=successful" | jq '[.[]| select(.workflows.job_name == "targetplatform")][0].build_num')
export MINORVERSION=3.3
export SEQUENCEVERSION=$(( 2800 + $TARGETBUILD ))
VERSION=${MINORVERSION}.${TARGETBUILD}
echo "Updating to version: ${VERSION} sequence: ${SEQUENCEVERSION}"
cat maven.template | sed "s/DEXELSBASEVERSION/${VERSION}/g" | \
	sed "s/SEQUENCE/${SEQUENCEVERSION}/g" \
	>maven.target
echo "$VERSION - $SEQUENCEVERSION" >VERSION
