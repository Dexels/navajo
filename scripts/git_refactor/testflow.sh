export BASEPATH=/Users/frank/git/spiritus/git
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR
export MODULEPATH=setup

echo "Remove work dir"
rm -rf work
	
echo "Making work dir"
mkdir work
cd work
mkdir navajo_initial
cd navajo_initial
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
echo "Creating initial repostitory"
cd ..
git clone --bare -l navajo_initial navajo_bare
echo "Converted to bare"

echo "genmerge: $BASEPATH/_com.dexels.target"
pwd
cd work
echo "Cloning working dir"
git clone navajo_bare navajo
cd navajo
#merge.sh com.dexels.target $BASEPATH/_com.dexels.target 
git remote add -f com.dexels.target_remote $BASEPATH/_com.dexels.target
echo "Added remote"
git merge --log -s ours --no-commit com.dexels.target_remote/master
git read-tree --prefix=TEMP/ -u com.dexels.target_remote/master
echo "MOVING: TEMP/$MODULEPATH TO " 
pwd
ls -l
git mv TEMP/$MODULEPATH .
rmdir TEMP

git commit -m "project merge"
git remote rm com.dexels.target_remote
#-----------------------------


#cd $DIR/work/navajo
#git push

