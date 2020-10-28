#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage $0 <RELEASEVERSION> <SNAPSHOTVERSION>"
fi

RELEASEVERSION=$1
SNAPSHOTVERSION=$2

cat META-INF/MANIFEST.MF | grep Bundle-Version
branch_name="$(git symbolic-ref HEAD 2>/dev/null)" ||
branch_name="(unnamed branch)"     # detached HEAD
branch_name=${branch_name##refs/heads/}
echo "Deploying in branch name: $branch_name"

commitMsg="Release of ${PWD##*/} version ${RELEASEVERSION}"
releaseTag="Release_${PWD##*/}-${RELEASEVERSION}"
environmentSwitch=" -Denvironment=release"

echo Setting version to ${RELEASEVERSION} and deploy
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=${RELEASEVERSION} $environmentSwitch
mvn deploy -DskipTests -Dmaven.test.failure.ignore=true  -Dbranch=$branch_name $environmentSwitch $SETREPO
git commit -m "$commitMsg" -a
git tag -a -f "$releaseTag" -m "$commitMsg"

echo Setting version to ${SNAPSHOTVERSION}-SNAPSHOT
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=${SNAPSHOTVERSION}-SNAPSHOT $environmentSwitch
git commit -m "Development stream of ${PWD##*/} version ${SNAPSHOTVERSION}" -a
git push --tags
git push

