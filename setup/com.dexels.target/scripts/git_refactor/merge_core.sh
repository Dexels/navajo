#!/bin/sh -ve
cd $1

echo $BASEPATH
echo $PATH

export MODULEPATH=setup

genmerge.sh com.dexels.repository 
genmerge.sh com.dexels.target 
genmerge.sh com.dexels.thirdparty.feature

export MODULEPATH=core

genmerge.sh com.dexels.navajo.core.feature
genmerge.sh com.dexels.navajo.version
genmerge.sh com.dexels.navajo.document
genmerge.sh com.dexels.navajo.core 
genmerge.sh com.dexels.navajo.client 
genmerge.sh com.dexels.navajo.client.async 
genmerge.sh com.dexels.navajo.function 
export MODULEPATH=server

genmerge.sh com.dexels.navajo.jsp.server 
genmerge.sh com.dexels.navajo.jsp 

cd ../..

