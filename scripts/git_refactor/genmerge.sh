echo "PREPENDING PRECHECK: repo: $REPOSITORY"
precheck.sh $1
echo "genmerge: $BASEPATH/_$1"
pwd
cd $REPOSITORY/$DIR/work
echo "CD'd to: ${REPOSITORY}/${DIR}/work"
git clone ${REPOSITORY}_bare ${REPOSITORY}/
cd ${REPOSITORY}/
merge.sh ${REPOSITORY}/$1 ${BASEPATH}/_$1 
cd ${DIR}/work/${REPOSITORY}/
git push
rm -rf ${DIR}/work/${REPOSITORY}/