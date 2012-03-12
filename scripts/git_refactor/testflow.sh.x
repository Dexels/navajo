export BASEPATH=/Users/frank/git/spiritus/git
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR

teardown.sh

mkdir work
cd work
mkdir navajo
cd navajo
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
cd work/navajo
git remote add -f com.dexels.navajo.client_remote $BASEPATH/com.dexels.navajo.client
#git merge --log -s subtree --no-commit com.dexels.navajo.client_remote/master 
echo "Reading navajo tree"


git checkout -b B_branch com.dexels.navajo.client_remote/master  # make a local branch following B's master
git filter-branch --index-filter \
        'git ls-files -s | sed "s-\t\"*-&/newsubdir-" |
                GIT_INDEX_FILE=$GIT_INDEX_FILE.new \
                        git update-index --index-info &&
         mv $GIT_INDEX_FILE.new $GIT_INDEX_FILE' HEAD
#git filter-branch --index-filter 'git ls-files -s | sed "s-\t\"*-&/com.dexels.navajo.client-" |GIT_INDEX_FILE=$GIT_INDEX_FILE.new git update-index --index-info && mv "$GIT_INDEX_FILE.new" "$GIT_INDEX_FILE"' HEAD 


git checkout master
git merge B_branch


cd ..
git clone navajo navajo_cloned
cd navajo_cloned/com.dexels.navajo.client
ls -la
git log adapters.xml
