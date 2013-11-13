#!/bin/bash
cat META-INF/MANIFEST.MF | grep Bundle-Version
branch_name="$(git symbolic-ref HEAD 2>/dev/null)" ||
branch_name="(unnamed branch)"     # detached HEAD

branch_name=${branch_name##refs/heads/}
echo "Deploying in branch name: $branch_name"
if [ "$branch_name" = "test" ]; then
   commitMsg = "Test release of ${PWD##*/} version $1" 
   releaseTag = "Test_${PWD##*/}-$1" 
else
   commitMsg = "Release of ${PWD##*/} version $1" 
   releaseTag = "Release_${PWD##*/}-$1" 
fi
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$1
mvn deploy
git commit -m "$commitMsg" -a
git tag -a -f "$releaseTag" -m "$commitMsg"
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$2-SNAPSHOT
mvn deploy
git commit -m "Development stream of ${PWD##*/} version $2" -a
git push --tags
git push
