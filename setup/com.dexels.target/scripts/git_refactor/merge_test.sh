#!/bin/sh -ve
cd $1

echo $BASEPATH
echo $PATH

export REPOSITORY=navajo
export MODULEPATH=setup

genmerge.sh com.dexels.repository 
genmerge.sh com.dexels.target 

export MODULEPATH=core

genmerge.sh com.dexels.navajo.core.feature
genmerge.sh com.dexels.navajo.core 

genmerge.sh com.dexels.navajo.server
genmerge.sh com.dexels.navajo.server.bridged
genmerge.sh com.dexels.navajo.server.bridged.deploy

export MODULEPATH=enterprise
export REPOSITORY=enterprise

#genmerge.sh com.dexels.navajo.tools.wsdl com.dexels.navajo.wsdl
genmerge.sh com.dexels.navajo.wsdl.feature
genmerge.sh com.dexels.navajo.enterprise.feature
genmerge.sh com.dexels.navajo.oda.feature
genmerge.sh com.dexels.navajo.oda
genmerge.sh com.dexels.navajo.oda.ui

export MODULEPATH=tipilesson
export REPOSITORY=navajo

genmerge.sh tipi.lesson.one


