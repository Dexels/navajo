echo "Making work dir"
mkdir work
cd work
mkdir navajo_initial
cd navajo_initial
git init
touch .gitignore
git add .gitignore
git commit -am "initial commit"
echo "Creating initial repostitory"
cd ..
git clone --bare -l navajo_initial navajo_bare
echo "Converted to bare"

echo "genmerge: $BASEPATH/_com.dexels.target"
pwd
echo "Cloning working dir"
git clone navajo_bare navajo
cd navajo
