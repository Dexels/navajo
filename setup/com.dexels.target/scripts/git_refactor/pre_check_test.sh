#!/bin/sh -ve
shopt -s extglob
export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.


echo $BASEPATH
echo $PATH

export MODULEPATH=setup

precheck.sh com.dexels.repository 
