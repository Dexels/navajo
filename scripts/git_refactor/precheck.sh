#!/bin/sh -ve
#export MODULEPATH=bundle
shopt -s extglob
cd $BASEPATH/
rm -rf _$1
mkdir _$1
cd _$1
#git clone ../$1 .
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
#git mv -k $FILELIST  $MODULEPATH/$1
#git mv -k  .cvsignore  $MODULEPATH/$1
#git mv -k  .project  $MODULEPATH/$1
#git mv -k  .classpath  $MODULEPATH/$1
#git mv -k  .settings  $MODULEPATH/$1
git add .
git commit -m "Prepare for move"
echo "Cleaning filter branch"
#git filter-branch --subdirectory-filter $MODULEPATH/$1 -- -- all
