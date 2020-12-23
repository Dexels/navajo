#!/bin/sh

set -o errexit


# This script checks the latest build numbers from circleci and updates  the  versions in the target file accordingly.
# The script exits with exit code 1 when no versions are found. This will cause the circleci script to bail out as expected.

if [ -z "${BRANCH}" ]; then
    export BRANCH="master"
fi

export TARGETBUILD=$(
	curl -s "https://circleci.com/api/v1.1/project/github/Dexels/dexels-base?circle-token=${CIRCLE_TOKEN}&offset=0&filter=successful" | jq "[.[]| select(.workflows.job_name == \"targetplatform\" and .branch == \"${BRANCH}\")][0].build_num"
)
if [ "$TARGETBUILD" = "null" ]; then
   echo "No version found for dexels-base"
   exit 1
fi


if [ "${BRANCH}" = "master" ]; then
  SUFFXIX=""
  echo "Using dependencies for master"
else
  SUFFIX="-${BRANCH}"
  echo "Using dependencies for $BRANCH branch."
fi

export MINORVERSION=3.3
export SEQUENCEVERSION=$(( 2800 + $TARGETBUILD ))
VERSION=${MINORVERSION}.${TARGETBUILD}

echo "Upgrading to dexels-base version: ${VERSION} sequence: ${SEQUENCEVERSION}"
cat maven.template | \
  sed "s/##DEXELSBASEVERSION##/${VERSION}/g" | \
	sed "s/##DEXELSBASESUFFIX##/${SUFFIX}/g" | \
  sed "s/##SEQUENCE##/${SEQUENCEVERSION}/g"	\
  > maven.target
echo "$VERSION - $SEQUENCEVERSION" > VERSION
