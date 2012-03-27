echo "Making work dir: $1"
mkdir $1_initial
cd $1_initial
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
echo "Creating initial repostitory"
cd ..
git clone --bare -l $1_initial $1_bare
echo "Converted to bare"

echo "genmerge: $BASEPATH/_com.dexels.target"
pwd
echo "Cloning working dir"
git clone $1_bare $1
#cd $1
