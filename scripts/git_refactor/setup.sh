#!/bin/sh -ve
echo "Making work dir: ${BASEPATH}/${1}"
mkdir ${BASEPATH}/${1}_initial
cd ${BASEPATH}/${1}_initial
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
echo "Creating initial repostitory"
cd ..
git clone --bare -l ${BASEPATH}/${1}_initial ${BASEPATH}/${1}_bare
echo "Converted to bare"

echo "genmerge: ${BASEPATH}/_com.dexels.target"
pwd
echo "Cloning working dir"
git clone ${BASEPATH}/${1}_bare ${BASEPATH}/${1}
#cd $1
