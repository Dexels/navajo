#!/bin/bash

cat META-INF/MANIFEST.MF | grep Bundle-Version
branch_name="$(git symbolic-ref HEAD 2>/dev/null)" ||
branch_name="(unnamed branch)"     # detached HEAD
branch_name=${branch_name##refs/heads/}
echo "Deploying in branch name: $branch_name"

commitMsg="Release of ${PWD##*/} version $1" 
releaseTag="Release_${PWD##*/}-$1" 
environmentSwitch=" -Denvironment=release"

mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$1 $environmentSwitch
mvn deploy -DskipTests -Dmaven.test.failure.ignore=true  -Dbranch=$branch_name $environmentSwitch $SETREPO
git commit -m "$commitMsg" -a
git tag -a -f "$releaseTag" -m "$commitMsg"

mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$2-SNAPSHOT $environmentSwitch
mvn deploy -DskipTests -Dmaven.test.failure.ignore=true  -Dbranch=$branch_name $environmentSwitch $SETREPO
git commit -m "Development stream of ${PWD##*/} version $2" -a
git push --tags
git push

