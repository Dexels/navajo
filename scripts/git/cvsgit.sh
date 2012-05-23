rm -rf $2
rm -rf cvs2svn-tmp
cvs2git  --username gitmonkey --fallback-encoding=ascii --encoding=utf_8 --blobfile=$2.blob --dumpfile=$2.dump /home/cvs/$1
mkdir $2
cd $2 
git init --bare
cat ../$2.blob ../$2.dump | git fast-import
git update-server-info
cd ..
rm -f $2.blob $2.dump
echo "-------================--------========"
echo "-------================--------========"
echo "-------================--------========"
echo "-------================--------========"
cd $2
/usr/local/cvs2svn/contrib/git-move-refs.py
cd ..
rm -rf /var/www/git/$2
mv $2 /var/www/git/
cp ${DIR}/_gitignore /var/www/git/$2/.gitignore
