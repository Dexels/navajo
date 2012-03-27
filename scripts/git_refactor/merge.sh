#!/bin/sh -ve
echo "MERGING project: $1 into branch: $1_remote with source: $2 and module name: $MODULEPATH"
echo "Using repo: $3"
git remote add -f $1_remote $2
git merge --log -s ours --no-commit $1_remote/master
git read-tree --prefix=TEMP/ -u $1_remote/master
echo "Module: $MODULEPATH"
pwd
mkdir -p $MODULEPATH/
git mv TEMP/$MODULEPATH/$1 $MODULEPATH/$1
rm -rf TEMP
git commit -m "project merge"
git remote rm $1_remote
