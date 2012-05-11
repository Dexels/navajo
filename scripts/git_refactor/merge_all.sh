#!/bin/sh -ve
#cd $1

echo $BASEPATH
echo $PATH

#export MODULEPATH=platform
#export REPOSITORY=targetplatform

#genmerge.sh com.dexels.thirdparty.feature

export MODULEPATH=setup
export REPOSITORY=navajo

genmerge.sh com.dexels.repository 
genmerge.sh com.dexels.target 
genmerge.sh com.dexels.opensource

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
genmerge.sh com.dexels.navajo.server.embedded
genmerge.sh com.dexels.navajo.server.feature
genmerge.sh com.dexels.navajo.server.embedded.feature
genmerge.sh com.dexels.navajo.queuemanager
genmerge.sh com.dexels.navajo.listeners 
genmerge.sh com.dexels.navajo.listeners.continuations
genmerge.sh com.dexels.navajo.adapters 
genmerge.sh com.dexels.navajo.rhino
genmerge.sh com.dexels.navajo.rhino.continuations
genmerge.sh com.dexels.navajo.resource.feature
genmerge.sh com.dexels.navajo.resource
genmerge.sh com.dexels.navajo.resource.jdbc.h2
genmerge.sh com.dexels.navajo.resource.jdbc.mysql
genmerge.sh com.dexels.navajo.resource.jdbc.oracle
genmerge.sh com.dexels.navajo.resource.mongodb
genmerge.sh com.dexels.navajo.resource.test
genmerge.sh com.dexels.navajo.resource.manager
genmerge.sh com.dexels.navajo.server
genmerge.sh com.dexels.navajo.server.bridged
genmerge.sh com.dexels.navajo.server.deploy
genmerge.sh com.dexels.navajo.server.bridged.deploy
genmerge.sh com.dexels.navajo.authentication.api

export MODULEPATH=enterprise
export REPOSITORY=internal

# this one isn't correct, the second arg is ignored
genmerge.sh com.dexels.navajo.wsdl
genmerge.sh com.dexels.navajo.wsdl.feature
genmerge.sh com.dexels.navajo.enterprise.feature
genmerge.sh com.dexels.navajo.enterprise.listeners
genmerge.sh com.dexels.navajo.enterprise.adapters
genmerge.sh com.dexels.navajo.enterprise
genmerge.sh com.dexels.navajo.enterprise.listeners.deps
genmerge.sh com.dexels.navajo.enterprise.adapters.deps
genmerge.sh com.dexels.navajo.mongo.feature
genmerge.sh com.dexels.navajo.mongo
genmerge.sh com.dexels.navajo.mongo.navajostore

genmerge.sh com.dexels.navajo.enterprise.ideal
genmerge.sh com.dexels.navajo.enterprise.ideal.sportlink
genmerge.sh com.dexels.navajo.enterprise.openfire
genmerge.sh com.dexels.navajo.enterprise.tomahawk
genmerge.sh com.dexels.navajo.enterprise.dbreplication
genmerge.sh com.dexels.navajo.enterprise.lucene

export MODULEPATH=other
export REPOSITORY=internal

genmerge.sh com.dexels.navajo.other.feature
genmerge.sh com.dexels.navajo.test.feature
genmerge.sh com.dexels.navajo.test.remote
genmerge.sh com.dexels.geospatial.imageprovider
genmerge.sh com.dexels.utilities.smsgateway

export MODULEPATH=demos
#genmerge.sh com.dexels.demos.rdfbrowser
genmerge.sh com.dexels.demos.ticketing
genmerge.sh com.dexels.demos.peelpage
genmerge.sh com.dexels.demos.pinkroccade.adapters 
genmerge.sh com.dexels.demos.pinkroccade.ei
genmerge.sh com.dexels.demos.stadiumdesigner
genmerge.sh com.dexels.demos.tipidashboard
genmerge.sh com.dexels.navajo.dashboard

export MODULEPATH=papers
genmerge.sh com.dexels.documents
genmerge.sh com.dexels.papers
genmerge.sh com.dexels.documents.other
genmerge.sh com.dexels.documents.ticketing

export MODULEPATH=dev
export REPOSITORY=navajo

genmerge.sh com.dexels.navajo.birt.push 
genmerge.sh com.dexels.navajo.dev.feature 
genmerge.sh com.dexels.navajo.dev.script 
genmerge.sh com.dexels.navajo.tipi.swt.client 
genmerge.sh com.dexels.navajo.dsl.navajomanager
genmerge.sh com.dexels.navajo.dsl.expression
genmerge.sh com.dexels.navajo.dsl.expression.model
genmerge.sh com.dexels.navajo.dsl.expression.ui
genmerge.sh com.dexels.navajo.dsl.tsl
genmerge.sh com.dexels.navajo.dsl.tsl.model
genmerge.sh com.dexels.navajo.dsl.tsl.ui
genmerge.sh com.dexels.navajo.dsl.integration
genmerge.sh com.dexels.navajo.dsl.feature
genmerge.sh com.dexels.navajo.remotetest
genmerge.sh com.dexels.navajo.dev.navadoc


export MODULEPATH=ios
genmerge.sh com.dexels.navajo.cocoa.client

export MODULEPATH=php
genmerge.sh com.dexels.navajo.client.php
genmerge.sh com.dexels.navajo.client.php.library
genmerge.sh com.dexels.navajo.php.joomla


export REPOSITORY=sportlink
export MODULEPATH=libraries


genmerge.sh com.sportlink.tipi.facilityoccupation
genmerge.sh com.sportlink.client
genmerge.sh com.sportlink.adapters
genmerge.sh com.sportlink.crystal
genmerge.sh com.sportlink.aaa
genmerge.sh com.sportlink.comp
genmerge.sh com.sportlink.tensing
genmerge.sh com.sportlink.swing.client
genmerge.sh com.sportlink.nevobo.dps
genmerge.sh com.sportlink.reports
genmerge.sh com.sportlink.dba

export MODULEPATH=applications

genmerge.sh com.sportlink.serv
genmerge.sh com.sportlink.dwf.av
genmerge.sh com.sportlink.club 
genmerge.sh com.sportlink.matchforms
genmerge.sh com.sportlink.officialportal
genmerge.sh com.sportlink.digitaalinschrijven
genmerge.sh com.sportlink.backoffice
genmerge.sh com.sportlink.accommodations
genmerge.sh com.sportlink.cif
genmerge.sh com.sportlink.dwf.dashboard
genmerge.sh com.sportlink.dashboard
genmerge.sh com.sportlink.teamregistration
genmerge.sh com.sportlink.webshop

export MODULEPATH=documents
genmerge.sh com.sportlink.analysis


#customized, to change the names:

export REPOSITORY=navajo


export MODULEPATH=demos

#genmerge.sh tipi.lesson.one
#genmerge.sh tipi.lesson.two
#genmerge.sh tipi.lesson.three
#genmerge.sh tipi.lesson.four
#genmerge.sh tipi.lesson.five
#genmerge.sh tipi.lesson.six

export MODULEPATH=optional

genmerge.sh com.dexels.navajo.utilities
genmerge.sh com.dexels.navajo.oda.feature
genmerge.sh com.dexels.navajo.oda
genmerge.sh com.dexels.navajo.oda.ui
genmerge.sh com.dexels.navajo.twitter
genmerge.sh com.dexels.navajo.twitter.feature
genmerge.sh com.dexels.navajo.svg
genmerge.sh com.dexels.navajo.geo 
genmerge.sh com.dexels.navajo.kml
genmerge.sh com.dexels.navajo.geo.feature
genmerge.sh com.dexels.navajo.workflow.editor
genmerge.sh com.dexels.navajo.workflow.editor.gmf
#genmerge.sh com.dexels.navajo.workflow.editor.feature
genmerge.sh com.dexels.navajo.function.pdf
genmerge.sh com.dexels.navajo.fileuploadservlet




export MODULEPATH=tipi

genmerge.sh com.dexels.navajo.tipi
genmerge.sh com.dexels.navajo.tipi.feature
genmerge.sh com.dexels.navajo.tipi.swing.feature
genmerge.sh com.dexels.navajo.tipi.swing.client
genmerge.sh com.dexels.navajo.tipi.swing
genmerge.sh com.dexels.navajo.tipi.swing.deps
genmerge.sh com.dexels.navajo.tipi.swing.application
genmerge.sh com.dexels.navajo.tipi.echo
genmerge.sh com.dexels.navajo.tipi.echo.client
genmerge.sh com.dexels.navajo.tipi.echo.feature
genmerge.sh com.dexels.navajo.tipi.vaadin
genmerge.sh com.dexels.navajo.tipi.vaadin.bridged
genmerge.sh com.dexels.navajo.tipi.vaadin.embedded
genmerge.sh com.dexels.navajo.tipi.vaadin.bridged.deploy
genmerge.sh com.dexels.navajo.tipi.vaadin.feature
genmerge.sh com.dexels.navajo.tipi.jcr
genmerge.sh com.dexels.navajo.tipi.jcr.feature
genmerge.sh com.dexels.navajo.tipi.jogl
genmerge.sh com.dexels.navajo.tipi.jogl.feature
genmerge.sh com.dexels.navajo.tipi.extensions.feature

genmerge.sh com.dexels.navajo.tipi.rcp
genmerge.sh com.dexels.navajo.tipi.rcp.feature
genmerge.sh com.dexels.navajo.tipi.swing.rdf.feature
genmerge.sh com.dexels.navajo.tipi.swing.rdf


genmerge.sh com.dexels.navajo.tipi.swing.docking.feature
genmerge.sh com.dexels.navajo.tipi.swing.docking
genmerge.sh com.dexels.navajo.tipi.swing.charting.feature
genmerge.sh com.dexels.navajo.tipi.swing.charting
genmerge.sh com.dexels.navajo.tipi.swing.editor.feature
genmerge.sh com.dexels.navajo.tipi.swing.editor
genmerge.sh com.dexels.navajo.swing.editor



genmerge.sh com.dexels.navajo.tipi.swing.svg.feature
genmerge.sh com.dexels.navajo.tipi.swing.svg
genmerge.sh com.dexels.navajo.tipi.swing.mig.feature
genmerge.sh com.dexels.navajo.tipi.swing.mig
genmerge.sh com.dexels.navajo.tipi.swing.substance.feature
genmerge.sh com.dexels.navajo.tipi.swing.substance
genmerge.sh com.dexels.navajo.tipi.swing.geo.feature
genmerge.sh com.dexels.navajo.tipi.swing.swingx
genmerge.sh com.dexels.navajo.tipi.swing.geo
genmerge.sh com.dexels.navajo.tipi.swing.jxlayer
genmerge.sh com.dexels.navajo.tipi.swing.rich.feature
genmerge.sh com.dexels.navajo.tipi.swing.rich.client
genmerge.sh com.dexels.navajo.tipi.swing.rich
genmerge.sh com.dexels.navajo.tipi.swing.cobra.feature
genmerge.sh com.dexels.navajo.tipi.swing.cobra
genmerge.sh com.dexels.navajo.tipi.flickr.feature
genmerge.sh com.dexels.navajo.tipi.flickr
genmerge.sh com.dexels.navajo.tipi.mail.feature
genmerge.sh com.dexels.navajo.tipi.mail
genmerge.sh com.dexels.navajo.tipi.jabber.feature
genmerge.sh com.dexels.navajo.tipi.jabber
genmerge.sh com.dexels.navajo.tipi.ruby.feature
genmerge.sh com.dexels.navajo.tipi.ruby
genmerge.sh com.dexels.navajo.tipi.rss.feature
genmerge.sh com.dexels.navajo.tipi.rss
genmerge.sh com.dexels.navajo.tipi.css 
genmerge.sh com.dexels.navajo.tipi.css.tkui
genmerge.sh com.dexels.navajo.tipi.css.feature

export MODULEPATH=tipi_dev

genmerge.sh com.dexels.navajo.tipi.dev.core
genmerge.sh com.dexels.navajo.tipi.dev.ant
genmerge.sh com.dexels.navajo.tipi.dev.plugin
genmerge.sh com.dexels.navajo.tipi.dev.feature
genmerge.sh com.dexels.navajo.tipi.build
genmerge.sh com.dexels.navajo.tipi.server



