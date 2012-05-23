#!/bin/sh -ve
shopt -s extglob
export BASEPATH=$1 #/Users/frank/git/spiritus/git
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PATH=$PATH:$DIR:.


echo $BASEPATH
echo $PATH

export MODULEPATH=setup

precheck.sh com.dexels.repository 
precheck.sh com.dexels.target 

export MODULEPATH=core

precheck.sh com.dexels.navajo.core.feature
precheck.sh com.dexels.navajo.version
precheck.sh com.dexels.navajo.document
precheck.sh com.dexels.navajo.core 
precheck.sh com.dexels.navajo.client 
precheck.sh com.dexels.navajo.client.async 
precheck.sh com.dexels.navajo.function 
precheck.sh com.dexels.navajo.authentication.api

export MODULEPATH=server

precheck.sh com.dexels.navajo.server.feature
precheck.sh com.dexels.navajo.server.embedded.feature
precheck.sh com.dexels.navajo.queuemanager
precheck.sh com.dexels.navajo.listeners 
precheck.sh com.dexels.navajo.listeners.continuations
precheck.sh com.dexels.navajo.adapters 
precheck.sh com.dexels.navajo.rhino
precheck.sh com.dexels.navajo.resource.feature
precheck.sh com.dexels.navajo.resource
precheck.sh com.dexels.navajo.resource.jdbc.h2
precheck.sh com.dexels.navajo.resource.jdbc.mysql
precheck.sh com.dexels.navajo.resource.jdbc.oracle
precheck.sh com.dexels.navajo.resource.mongodb
precheck.sh com.dexels.navajo.resource.test
precheck.sh com.dexels.navajo.resource.manager
precheck.sh com.dexels.navajo.server
precheck.sh com.dexels.navajo.server.bridged
precheck.sh com.dexels.navajo.server.bridged.deploy

export MODULEPATH=enterprise

precheck.sh com.dexels.navajo.wsdl.feature
precheck.sh com.dexels.navajo.wsdl
precheck.sh com.dexels.navajo.enterprise.feature
precheck.sh com.dexels.navajo.enterprise.listeners
precheck.sh com.dexels.navajo.enterprise.adapters
precheck.sh com.dexels.navajo.enterprise
precheck.sh com.dexels.navajo.enterprise.listeners.deps
precheck.sh com.dexels.navajo.enterprise.adapters.deps
precheck.sh com.dexels.navajo.mongo.feature
precheck.sh com.dexels.navajo.mongo
precheck.sh com.dexels.navajo.mongo.navajostore
precheck.sh com.dexels.navajo.other.feature
precheck.sh com.dexels.navajo.other.utilities
precheck.sh com.dexels.navajo.test.feature
precheck.sh com.dexels.navajo.test.remote
export MODULEPATH=server
precheck.sh com.dexels.navajo.authentication.api
precheck.sh com.dexels.navajo.birt.push 
precheck.sh com.dexels.navajo.jsp.server 
precheck.sh com.dexels.navajo.jsp 
precheck.sh com.dexels.navajo.server.embedded
export MODULEPATH=dev
precheck.sh com.dexels.navajo.remotetest
precheck.sh com.dexels.navajo.tipi.swt.client 
precheck.sh com.dexels.navajo.dev.feature 
precheck.sh com.dexels.navajo.dev.script 
precheck.sh com.dexels.navajo.dsl.navajomanager
precheck.sh com.dexels.navajo.dsl.expression
precheck.sh com.dexels.navajo.dsl.expression.model
precheck.sh com.dexels.navajo.dsl.expression.ui
precheck.sh com.dexels.navajo.dsl.tsl
precheck.sh com.dexels.navajo.dsl.tsl.model
precheck.sh com.dexels.navajo.dsl.tsl.ui
precheck.sh com.dexels.navajo.dsl.integration
precheck.sh com.dexels.navajo.dsl.feature



export MODULEPATH=sportlink


precheck.sh com.sportlink.tipi.facilityoccupation
precheck.sh com.sportlink.serv
precheck.sh com.sportlink.client
precheck.sh com.sportlink.adapters
precheck.sh com.sportlink.crystal
precheck.sh com.sportlink.aaa
precheck.sh com.sportlink.comp
precheck.sh com.sportlink.tensing
#customized, to change the names:
precheck.sh tipi.sportlink.officialportal
precheck.sh com.sportlink.swing.client

export MODULEPATH=other

precheck.sh com.dexels.navajo.utilities
precheck.sh com.dexels.navajo.oda.feature
precheck.sh com.dexels.navajo.oda
precheck.sh com.dexels.navajo.oda.ui

export MODULEPATH=tipilesson

precheck.sh tipi.lesson.one
precheck.sh tipi.lesson.two
precheck.sh tipi.lesson.three
precheck.sh tipi.lesson.four
precheck.sh tipi.lesson.five
precheck.sh tipi.lesson.six
precheck.sh com.dexels.navajo.twitter
precheck.sh com.dexels.navajo.twitter.feature
precheck.sh com.dexels.navajo.svg
precheck.sh com.dexels.navajo.geo 
precheck.sh com.dexels.navajo.kml
precheck.sh com.dexels.navajo.geo.feature
precheck.sh com.dexels.navajo.workflow.editor
precheck.sh com.dexels.navajo.workflow.editor.gmf
precheck.sh com.dexels.navajo.workflow.editor.feature


export MODULEPATH=tipi

precheck.sh com.dexels.navajo.tipi
precheck.sh com.dexels.navajo.tipi.feature
precheck.sh com.dexels.navajo.tipi.swing.feature
precheck.sh com.dexels.navajo.tipi.swing.client
precheck.sh com.dexels.navajo.tipi.swing
precheck.sh com.dexels.navajo.tipi.swing.deps
precheck.sh com.dexels.navajo.tipi.swing.application
precheck.sh com.dexels.navajo.tipi.echo
precheck.sh com.dexels.navajo.tipi.echo.client
precheck.sh com.dexels.navajo.tipi.echo.feature
precheck.sh com.dexels.navajo.tipi.vaadin
precheck.sh com.dexels.navajo.tipi.vaadin.bridged
precheck.sh com.dexels.navajo.tipi.vaadin.embedded
precheck.sh com.dexels.navajo.tipi.vaadin.bridged.deploy
precheck.sh com.dexels.navajo.tipi.vaadin.feature



precheck.sh com.dexels.navajo.tipi.swing.docking.feature
precheck.sh com.dexels.navajo.tipi.swing.docking
precheck.sh com.dexels.navajo.tipi.swing.charting.feature
precheck.sh com.dexels.navajo.tipi.swing.charting
precheck.sh com.dexels.navajo.tipi.swing.editor.feature
precheck.sh com.dexels.navajo.tipi.swing.editor
precheck.sh com.dexels.navajo.swing.editor



precheck.sh com.dexels.navajo.tipi.swing.svg.feature
precheck.sh com.dexels.navajo.tipi.swing.svg
precheck.sh com.dexels.navajo.tipi.swing.mig.feature
precheck.sh com.dexels.navajo.tipi.swing.mig
precheck.sh com.dexels.navajo.tipi.swing.substance.feature
precheck.sh com.dexels.navajo.tipi.swing.substance
precheck.sh com.dexels.navajo.tipi.swing.geo.feature
precheck.sh com.dexels.navajo.tipi.swing.swingx
precheck.sh com.dexels.navajo.tipi.swing.geo
precheck.sh com.dexels.navajo.tipi.swing.jxlayer
precheck.sh com.dexels.navajo.tipi.swing.rich.feature
precheck.sh com.dexels.navajo.tipi.swing.rich.client
precheck.sh com.dexels.navajo.tipi.swing.rich
precheck.sh com.dexels.navajo.tipi.swing.cobra.feature
precheck.sh com.dexels.navajo.tipi.swing.cobra
precheck.sh com.dexels.navajo.tipi.flickr.feature
precheck.sh com.dexels.navajo.tipi.flickr
precheck.sh com.dexels.navajo.tipi.mail.feature
precheck.sh com.dexels.navajo.tipi.mail
precheck.sh com.dexels.navajo.tipi.jabber.feature
precheck.sh com.dexels.navajo.tipi.jabber
precheck.sh com.dexels.navajo.tipi.ruby.feature
precheck.sh com.dexels.navajo.tipi.ruby
precheck.sh com.dexels.navajo.tipi.rss.feature
precheck.sh com.dexels.navajo.tipi.rss

export MODULEPATH=tipi_dev

precheck.sh com.dexels.navajo.tipi.dev.core
precheck.sh com.dexels.navajo.tipi.dev.ant
precheck.sh com.dexels.navajo.tipi.dev.plugin
precheck.sh com.dexels.navajo.tipi.dev.feature
precheck.sh com.dexels.navajo.tipi.build
precheck.sh com.dexels.navajo.tipi.server



