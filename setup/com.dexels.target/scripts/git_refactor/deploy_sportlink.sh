#!/bin/sh -ve
echo "Global setup done"
export BASEPATH=$1
#export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.
teardown.sh sportlink
teardown.sh sportlink_ios
echo "About to setup:"
setup.sh sportlink
setup.sh sportlink_ios
merge_sportlink.sh 
