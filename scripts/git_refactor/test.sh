#!/bin/sh -ve
export BASEPATH=$1
#export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.
teardown.sh
setup.sh navajo
merge_core.sh 
#merge_test.sh
echo "Pushing to $DIR/work/navajo"
git clone $DIR/work/navajo_bare $DIR/work/navajo_deploy