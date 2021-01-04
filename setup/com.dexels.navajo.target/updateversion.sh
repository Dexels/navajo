#!/bin/bash

# This script checks the latest build numbers from CircleCI and updates the versions in the target
# file accordingly. The script exits with exit code 1 when no versions are found. This will cause
# the CircleCI script to bail out as expected.

set -o errexit


if [ -z "${BRANCH}" ]; then
    BRANCH="master"
fi

if [ "${BRANCH}" = "master" ]; then
    SUFFXIX=""
    echo "Using dependencies for master"
else
    SUFFIX="-${BRANCH}"
    echo "Using dependencies for $BRANCH branch."
fi


TARGETBUILD=$(curl -s "https://circleci.com/api/v1.1/project/github/Dexels/dexels-base?circle-token=${CIRCLE_TOKEN}&offset=0&filter=successful" | jq "[.[]| select(.workflows.job_name == \"targetplatform\" and .branch == \"${BRANCH}\")][0].build_num")
if [ "$TARGETBUILD" == "null" ]; then
    echo "No version found for dexels-base"
    exit 1
fi
if [ -z "${MINORVERSION}"]; then
    MINORVERSION="3.3"
fi
VERSION="${MINORVERSION}.${TARGETBUILD}"
echo "dexels-base version: '$VERSION'"


SEQUENCEVERSION=$(( 2800 + $TARGETBUILD ))
echo "sequence version:    '$SEQUENCEVERSION'"


cat maven.template | \
    sed "s/##DEXELSBASEVERSION##/${VERSION}/g" | \
    sed "s/##DEXELSBASESUFFIX##/${SUFFIX}/g" | \
    sed "s/##SEQUENCE##/${SEQUENCEVERSION}/g" \
    > maven.target

echo "$VERSION"         > VERSION
echo "$SEQUENCEVERSION" > SEQUENCE

