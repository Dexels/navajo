#!/bin/bash
cat META-INF/MANIFEST.MF | grep Bundle-Version
branch_name="$(git symbolic-ref HEAD 2>/dev/null)" ||
branch_name="(unnamed branch)"     # detached HEAD

branch_name=${branch_name##refs/heads/}
echo "Deploying in branch name: $branch_name"
export commitMsg=blank
export releaseTag=blank
if [ "$branch_name" = "test" ]; then
   export commitMsg="Test release of ${PWD##*/} version $1" 
   export releaseTag="Test_${PWD##*/}-$1" 
else
   export commitMsg="Release of ${PWD##*/} version $1" 
   export releaseTag="Release_${PWD##*/}-$1" 
fi
echo "Message: ${commitMsg}"
echo "Tag: ${releaseTag}"
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$1
mvn deploy
git commit -m "$commitMsg" -a
git tag -a -f "$releaseTag" -m "$commitMsg"
mvn -Dtycho.mode=maven org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=$2-SNAPSHOT
mvn deploy
git commit -m "Development stream of ${PWD##*/} version $2" -a
git push --tags
git push
