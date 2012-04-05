#!/bin/sh -ve
#export MODULEPATH=bundle
echo "BasePath: ${BASEPATH}"
shopt -s extglob
cd $BASEPATH/
rm -rf _$1
mkdir _$1
cd _$1
git clone ${GITSOURCE}/$1 .
#git clone $BASEPATH/$1
echo "We are now here: $BASEPATH/_$1"
pwd
mkdir -p $MODULEPATH/$1
#FILELIST=$(ls -ad )
#echo "FILES: $FILELIST"
for i in `find . -maxdepth 1 ! -name $MODULEPATH ! -name '\.'`
do
 git mv -k  $i  $MODULEPATH/$1
done
git add .
git commit -m "Prepare for move"
echo "Cleaning filter branch"
#git filter-branch --subdirectory-filter $MODULEPATH/$1 -- -- all
