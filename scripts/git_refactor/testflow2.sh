export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.
teardown.sh
setup.sh navajo
export MODULEPATH=setup
cd work/navajo/
echo "MERGING project: com.dexels.repository into branch: com.dexels.repository_remote with source: $BASEPATH/_com.dexels.repository"
pwd
git remote add -f com.dexels.repository_remote $BASEPATH/_com.dexels.repository
git merge --log -s ours --no-commit com.dexels.repository_remote/master
git commit -m "project merge"
echo pullin
#git fetch com.dexels.repository_remote 

git read-tree --prefix=TEMP/  -u com.dexels.repository_remote/master
git mv TEMP/setup .
rmdir TEMP
git commit -m 'moved'
git remote rm com.dexels.repository_remote

