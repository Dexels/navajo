echo "genmerge: $BASEPATH/_$1"
pwd
cd $DIR/work
echo "CD'd to: $DIR/work"
git clone navajo_bare navajo
cd navajo
merge.sh $1 $BASEPATH/_$1 
cd $DIR/work/navajo
git push
rm -rf $DIR/work/navajo