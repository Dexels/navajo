echo "MERGING project: $1 into branch: $1_remote with source: $2 and module name: $MODULEPATH"

git remote add -f $1_remote $2
git merge --log -s ours --no-commit $1_remote/master
git read-tree --prefix=TEMP/ -u $1_remote/master
mkdir -p $MODULEPATH/
git mv TEMP/bundle/* $MODULEPATH/
rm -rf TEMP
git commit -m "project merge"
git remote rm $1_remote
