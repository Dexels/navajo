shopt -s extglob
export BASEPATH=/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.


echo $BASEPATH
echo $PATH

export MODULEPATH=setup

precheck.sh com.dexels.repository 
shopt -s extglob
cd $BASEPATH/
echo "cedeed to: $BASEPATH/"
rm -rf _com.dexels.repository
mkdir _com.dexels.repository
cd _com.dexels.repository
git clone ../com.dexels.repository .
echo "We are now here: $BASEPATH/_com.dexels.repository"
pwd
mkdir -p $MODULEPATH/com.dexels.repository
FILELIST=$(ls -ad !($MODULEPATH))
echo "FILES: $FILELIST"
git mv -k $FILELIST  $MODULEPATH/com.dexels.repository
git mv -k  .cvsignore  $MODULEPATH/com.dexels.repository
git mv -k  .project  $MODULEPATH/com.dexels.repository
git mv -k  .classpath  $MODULEPATH/com.dexels.repository
git mv -k  .settings  $MODULEPATH/com.dexels.repository
git add .
git commit -m "Prepare for move"
echo "Cleaning filter branch"
git filter-branch --subdirectory-filter $MODULEPATH/com.dexels.repository -- -- all
