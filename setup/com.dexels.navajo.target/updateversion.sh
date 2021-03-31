#!/bin/bash

# This script checks the latest build numbers from CircleCI and updates the versions in the target
# file accordingly. The script exits with exit code 1 when no versions are found. This will cause
# the CircleCI script to bail out as expected.

set -o errexit


if [ -z "$BRANCH" ] ; then
    BRANCH="master"
fi

if [ "$BRANCH" = "master" ]; then
    SUFFXIX=""
    echo "Using dependencies for master"
else
    SUFFIX="-$BRANCH"
    echo "Using dependencies for $BRANCH branch."
fi


if [ -z "$DEXELS_BASE_BUILD" ] ; then
    DEXELS_BASE_URL="https://circleci.com/api/v1.1/project/github/Dexels/dexels-base?circle-token=${CIRCLE_TOKEN}&offset=0&filter=successful"
    DEXELS_BASE_BUILD=$(curl -s "$DEXELS_BASE_URL" | jq "[.[]| select(.workflows.job_name == \"targetplatform\" and .branch == \"${BRANCH}\")][0].build_num")
    if [ "$DEXELS_BASE_BUILD" == "null" ] ; then
        echo "No version found for dexels-base"
        exit 1
    fi
    echo "Retrieved dexels-base build number '$DEXELS_BASE_BUILD'"
else
    echo "Provided dexels-base build number '$DEXELS_BASE_BUILD'"
fi
if [ -z "${DEXELS_BASE_PREFIX}"]; then
    DEXELS_BASE_PREFIX="3.3"
fi
DEXELS_BASE="${DEXELS_BASE_PREFIX}.${DEXELS_BASE_BUILD}"


SEQUENCEVERSION=$(( 2800 + $DEXELS_BASE_BUILD ))


echo "dexels-base version: '$DEXELS_BASE'"
echo "sequence version:    '$SEQUENCEVERSION'"


cat maven.template | \
    sed "s/##DEXELSBASEVERSION##/${DEXELS_BASE}/g" | \
    sed "s/##DEXELSBASESUFFIX##/${SUFFIX}/g" | \
    sed "s/##SEQUENCE##/${SEQUENCEVERSION}/g" \
    > maven.target

echo "$DEXELS_BASE"     > DEXELS_BASE
echo "$SEQUENCEVERSION" > SEQUENCE

