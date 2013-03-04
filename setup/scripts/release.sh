#!/bin/bash
cat META-INF/MANIFEST.MF | grep Bundle-Version
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$1
mvn deploy
git commit -m "Release of ${PWD##*/} version $1" -a
git tag -a -f "Release_${PWD##*/}-$1" -m "Component release: ${PWD##*/}-$1"
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$2-SNAPSHOT
#mvn deploy
git commit -m "Development stream of ${PWD##*/} version $2" -a
git push --tags
git push
