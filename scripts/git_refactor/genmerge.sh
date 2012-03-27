echo "PREPENDING PRECHECK: repo: $REPOSITORY"
precheck.sh $1
echo "genmerge: $BASEPATH/_$1"
pwd
cd ${BASEPATH}$REPOSITORY/$DIR/work
echo "CD'd to: ${BASEPATH}${REPOSITORY}/${DIR}/work"
git clone ${BASEPATH}${REPOSITORY}_bare ${REPOSITORY}/
cd ${BASEPATH}$REPOSITORY/$DIR/work/${REPOSITORY}/
merge.sh ${REPOSITORY}/$1 ${BASEPATH}/_$1 
cd ${DIR}/work/${REPOSITORY}/
git push
rm -rf ${DIR}/work/${REPOSITORY}/