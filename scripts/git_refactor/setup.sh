echo "Making work dir: ${BASEPATH}/$1"
mkdir ${BASEPATH}/$1_initial
cd ${BASEPATH}/$1_initial
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
echo "Creating initial repostitory"
cd ..
git clone --bare -l ${BASEPATH}/$1_initial ${BASEPATH}/$1_bare
echo "Converted to bare"

echo "genmerge: $BASEPATH/_com.dexels.target"
pwd
echo "Cloning working dir"
git clone ${BASEPATH}/$1_bare ${BASEPATH}/$1
#cd $1
