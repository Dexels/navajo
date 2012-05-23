#!/bin/sh -ve
echo "Global setup done"
export BASEPATH=$1
#export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.
teardown.sh navajo
teardown.sh navajo_ios
teardown.sh navajo_php
teardown.sh sportlink
teardown.sh sportlink_ios
teardown.sh internal
teardown.sh targetplatform
teardown.sh enterprise
echo "About to setup:"
setup.sh navajo
setup.sh sportlink
setup.sh sportlink_ios
setup.sh internal
setup.sh targetplatform
setup.sh navajo_ios
setup.sh navajo_php
setup.sh enterprise
merge_all.sh 
