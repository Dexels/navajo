#!/bin/sh -ve
echo "Remove work dir: ${BASEPATH}/${1}_initial"
rm -rf ${BASEPATH}/${1}_initial
echo "Remove work dir: ${BASEPATH}/${1}"
rm -rf ${BASEPATH}/${1}
echo "Remove work dir: ${BASEPATH}/${1}_bare"
rm -rf ${BASEPATH}/${1}_bare

#rm -rf work
