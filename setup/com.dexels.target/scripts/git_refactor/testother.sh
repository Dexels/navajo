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
cd ../
mkdir transfer
cd transfer
git clone $BASEPATH/com.dexels.navajo.core
cd com.dexels.navajo.core
mkdir com.dexels.navajo.core
git mv src testsrc META-INF OSGI-INF build* lib compilelib pom* doc com.dexels.navajo.core
git commit -m "aap"
git filter-branch --subdirectory-filter com.dexels.navajo.core -- -- all
pwd
cd ../../navajo
git remote add import ../transfer/com.dexels.navajo.core
git fetch import
git branch import remotes/import/master
	git merge import