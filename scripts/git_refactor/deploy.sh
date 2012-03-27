git config --global user.name "Dexels CVS migration"
git config --global user.email info@dexels.com
git commit --amend --reset-author
echo "Global setup done"
export BASEPATH=$1
#export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.
teardown.sh
echo "About to setup:"
setup.sh navajo
setup.sh sportlink
setup.sh enterprise
#merge_core.sh 
#merge_all.sh 
merge_test.sh
echo "Pushing to $DIR/work/navajo"
git clone $DIR/work/navajo_bare $DIR/work/navajo_deploy