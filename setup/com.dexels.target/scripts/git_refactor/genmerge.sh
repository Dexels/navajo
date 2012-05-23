#!/bin/sh -ve
echo "PREPENDING PRECHECK: repo: $REPOSITORY"
precheck.sh $1
echo "genmerge: $BASEPATH/_$1"
pwd
cd $BASEPATH
#mkdir  $DIR/work
#cd $DIR/work
#echo "CD'd to: ${DIR}/work"
#git clone ${BASEPATH}${REPOSITORY}_bare ${REPOSITORY}/
cd ${BASEPATH}${REPOSITORY}/
merge.sh $1 ${BASEPATH}/_$1 ${REPOSITORY}
#cd ${DIR}/work/${REPOSITORY}/
#git push
#rm -rf ${DIR}/work/${REPOSITORY}/