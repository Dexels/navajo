cd $1

echo $BASEPATH
echo $PATH


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

genmerge.sh com.dexels.navajo.tools.wsdl com.dexels.navajo.wsdl
genmerge.sh com.dexels.navajo.wsdl.feature
genmerge.sh com.dexels.navajo.enterprise.feature
genmerge.sh com.dexels.navajo.oda.feature
genmerge.sh com.dexels.navajo.oda
genmerge.sh com.dexels.navajo.oda.ui

export MODULEPATH=tipilesson

genmerge.sh tipi.lesson.one
genmerge.sh tipi.lesson.two
genmerge.sh tipi.lesson.three
genmerge.sh tipi.lesson.four
genmerge.sh tipi.lesson.five
genmerge.sh tipi.lesson.six
genmerge.sh com.dexels.navajo.twitter
genmerge.sh com.dexels.navajo.twitter.feature
genmerge.sh com.dexels.navajo.svg
genmerge.sh com.dexels.navajo.geo 
genmerge.sh com.dexels.navajo.kml
genmerge.sh com.dexels.navajo.geo.feature
genmerge.sh com.dexels.navajo.workflow.editor
genmerge.sh com.dexels.navajo.workflow.editor.gmf
genmerge.sh com.dexels.navajo.workflow.editor.feature


export MODULEPATH=tipi

genmerge.sh com.dexels.navajo.tipi
genmerge.sh com.dexels.navajo.tipi.feature
genmerge.sh com.dexels.navajo.tipi.swing.feature
genmerge.sh com.dexels.navajo.tipi.swing.client
genmerge.sh com.dexels.navajo.tipi.swing

cd ../..

