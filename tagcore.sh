#cd ../..
# from Navajo OS root
cd setup
tosnap.sh com.dexels.repository 1.0.1 
tosnap.sh com.dexels.target 1.0.1 
cd ../core
tosnap.sh com.dexels.navajo.api 1.0.2
tosnap.sh com.dexels.navajo.client 3.4.2
tosnap.sh com.dexels.navajo.client.async 1.1.2
tosnap.sh com.dexels.navajo.client.jabber 1.0.2
tosnap.sh com.dexels.navajo.core 2.9.9

tosnap.sh com.dexels.navajo.core.feature 1.0.9
tosnap.sh com.dexels.navajo.document 2.2.2
tosnap.sh com.dexels.navajo.function 2.0.9
tosnap.sh com.dexels.navajo.listeners.external 1.0.2
tosnap.sh com.dexels.navajo.version 1.0.22
cd ../server
tosnap.sh com.dexels.navajo.adapters 1.0.7
tosnap.sh com.dexels.navajo.authentication.api 1.0.2
tosnap.sh com.dexels.navajo.jsp 1.0.4
tosnap.sh com.dexels.navajo.jsp.server 1.0.5
tosnap.sh com.dexels.navajo.listeners 3.1.8
tosnap.sh com.dexels.navajo.listeners.continuations 1.0.5
tosnap.sh com.dexels.navajo.queuemanager 1.0.5
tosnap.sh com.dexels.navajo.resource 1.0.2
tosnap.sh com.dexels.navajo.resource.feature 1.0.2
tosnap.sh com.dexels.navajo.resource.jdbc.h2 1.0.2
tosnap.sh com.dexels.navajo.resource.jdbc.mysql 1.0.2
tosnap.sh com.dexels.navajo.resource.jdbc.oracle 1.0.2
tosnap.sh com.dexels.navajo.resource.manager 1.0.2
tosnap.sh com.dexels.navajo.resource.mongodb 1.0.2
tosnap.sh com.dexels.navajo.resource.test 1.0.2



tosnap.sh com.dexels.navajo.rhino 1.1.2
tosnap.sh com.dexels.navajo.rhino.continuations 1.0.2
tosnap.sh com.dexels.navajo.server.bridged 1.0.1
tosnap.sh com.dexels.navajo.server.embedded 1.0.4
tosnap.sh com.dexels.navajo.server.embedded.feature 1.0.4
tosnap.sh com.dexels.navajo.server.feature 1.0.6
#not ready yet
#tosnap.sh com.dexels.navajo.war #besides, this is NOT the right one, use the one in runtime
tosnap.sh com.dexels.navajo.webconsole.branding 1.0.2
cd ../dev
tosnap.sh com.dexels.navajo.birt.push 1.0.2
tosnap.sh com.dexels.navajo.dev.feature 2.0.6
tosnap.sh com.dexels.navajo.dev.navadoc 1.0.2
tosnap.sh com.dexels.navajo.dev.script 2.0.6
tosnap.sh com.dexels.navajo.dsl.expression 1.1.1
tosnap.sh com.dexels.navajo.dsl.expression.model 1.1.1
tosnap.sh com.dexels.navajo.dsl.expression.ui 1.1.1
tosnap.sh com.dexels.navajo.dsl.feature 1.1.1
tosnap.sh com.dexels.navajo.dsl.integration 1.1.1
tosnap.sh com.dexels.navajo.dsl.navajomanager 1.1.1
tosnap.sh com.dexels.navajo.dsl.tsl 1.1.1
tosnap.sh com.dexels.navajo.dsl.tsl.model 1.1.1
tosnap.sh com.dexels.navajo.dsl.tsl.ui 1.1.1


# No pom yet, not mentioned in features / allgit's

#tosnap.sh com.dexels.navajo.osgi.console
tosnap.sh com.dexels.navajo.tipi.swt.client 1.0.4
cd ../optional
tosnap.sh com.dexels.navajo.fileuploadservlet 1.0.2
tosnap.sh com.dexels.navajo.function.pdf 1.0.1
tosnap.sh com.dexels.navajo.geo 1.0.3
tosnap.sh com.dexels.navajo.geo.feature 1.0.3
tosnap.sh com.dexels.navajo.kml 1.0.3
tosnap.sh com.dexels.navajo.oda 1.0.4
tosnap.sh com.dexels.navajo.oda.feature 1.0.4
tosnap.sh com.dexels.navajo.oda.ui 1.0.4
tosnap.sh com.dexels.navajo.other.feature 1.0.2
tosnap.sh com.dexels.navajo.svg 1.0.3
tosnap.sh com.dexels.navajo.test.feature 1.0.2
tosnap.sh com.dexels.navajo.test.remote 1.0.2
tosnap.sh com.dexels.navajo.twitter 1.0.3
tosnap.sh com.dexels.navajo.twitter.feature 1.0.3
tosnap.sh com.dexels.navajo.utilities 1.0.3
tosnap.sh com.dexels.navajo.workflow.editor 1.0.2
tosnap.sh com.dexels.navajo.workflow.editor.feature 1.0.2
tosnap.sh com.dexels.navajo.workflow.editor.gmf 1.0.2


cd ../runtime
tosnap.sh com.dexels.navajo.runtime.feature 1.0.2
tosnap.sh com.dexels.navajo.runtime.osgi 1.0.2
tosnap.sh com.dexels.navajo.runtime.osgi.j2ee 1.0.2
#tosnap.sh com.dexels.navajo.war 1.0.2
cd ../tipi
tosnap.sh com.dexels.navajo.swing.editor 1.1.3
tosnap.sh com.dexels.navajo.tipi 1.1.16
tosnap.sh com.dexels.navajo.tipi.css 1.0.12
tosnap.sh com.dexels.navajo.tipi.css.feature 1.0.12
tosnap.sh com.dexels.navajo.tipi.css.tkui 1.0.12
tosnap.sh com.dexels.navajo.tipi.echo 1.0.28
tosnap.sh com.dexels.navajo.tipi.echo.client 1.0.28
tosnap.sh com.dexels.navajo.tipi.echo.feature 1.0.28
tosnap.sh com.dexels.navajo.tipi.extensions.feature 1.0.2
tosnap.sh com.dexels.navajo.tipi.feature 1.1.16



tosnap.sh com.dexels.navajo.tipi.flickr 1.0.20
tosnap.sh com.dexels.navajo.tipi.flickr.feature 1.0.20
tosnap.sh com.dexels.navajo.tipi.jabber 1.0.13
tosnap.sh com.dexels.navajo.tipi.jabber.feature 1.0.13
tosnap.sh com.dexels.navajo.tipi.jcr 1.0.2
tosnap.sh com.dexels.navajo.tipi.jcr.feature 1.0.2
tosnap.sh com.dexels.navajo.tipi.jogl 1.0.2
tosnap.sh com.dexels.navajo.tipi.jogl.feature 1.0.2
tosnap.sh com.dexels.navajo.tipi.mail 1.0.25
tosnap.sh com.dexels.navajo.tipi.mail.feature 1.0.25
tosnap.sh com.dexels.navajo.tipi.rcp 1.0.2
tosnap.sh com.dexels.navajo.tipi.rcp.feature 1.0.2
tosnap.sh com.dexels.navajo.tipi.rss 1.0.9
tosnap.sh com.dexels.navajo.tipi.rss.feature 1.0.9


tosnap.sh com.dexels.navajo.tipi.ruby 1.0.5
tosnap.sh com.dexels.navajo.tipi.ruby.feature 1.0.5
tosnap.sh com.dexels.navajo.tipi.swing 1.2.1
tosnap.sh com.dexels.navajo.tipi.swing.application 1.2.1
tosnap.sh com.dexels.navajo.tipi.swing.charting 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.charting.feature 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.client 1.2.1
tosnap.sh com.dexels.navajo.tipi.swing.cobra 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.cobra.feature 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.deps 1.2.1
tosnap.sh com.dexels.navajo.tipi.swing.docking 1.0.6
tosnap.sh com.dexels.navajo.tipi.swing.docking.feature 1.0.6
tosnap.sh com.dexels.navajo.tipi.swing.editor 1.1.3
tosnap.sh com.dexels.navajo.tipi.swing.editor.feature 1.1.3
tosnap.sh com.dexels.navajo.tipi.swing.feature 1.2.1



tosnap.sh com.dexels.navajo.tipi.swing.geo 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.geo.feature 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.jxlayer 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.mig 1.0.9
tosnap.sh com.dexels.navajo.tipi.swing.mig.feature 1.0.9
tosnap.sh com.dexels.navajo.tipi.swing.rdf 1.0.2
tosnap.sh com.dexels.navajo.tipi.swing.rdf.feature 1.0.2
tosnap.sh com.dexels.navajo.tipi.swing.rich 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.rich.client 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.rich.feature 1.0.11
tosnap.sh com.dexels.navajo.tipi.swing.substance 1.1.6
tosnap.sh com.dexels.navajo.tipi.swing.substance.feature 1.1.6
tosnap.sh com.dexels.navajo.tipi.swing.svg 1.0.7
tosnap.sh com.dexels.navajo.tipi.swing.svg.feature 1.0.7
tosnap.sh com.dexels.navajo.tipi.swing.swingx 1.0.11
tosnap.sh com.dexels.navajo.tipi.vaadin 1.0.4
tosnap.sh com.dexels.navajo.tipi.vaadin.bridged 1.0.4
tosnap.sh com.dexels.navajo.tipi.vaadin.bridged.deploy 1.0.4
tosnap.sh com.dexels.navajo.tipi.vaadin.embedded 1.0.4
tosnap.sh com.dexels.navajo.tipi.vaadin.feature 1.0.4
cd ../tipi_dev
#tosnap.sh com.dexels.navajo.tipi.build
tosnap.sh com.dexels.navajo.tipi.dev.ant 1.1.1
tosnap.sh com.dexels.navajo.tipi.dev.core 1.1.1
tosnap.sh com.dexels.navajo.tipi.dev.feature 1.1.1
tosnap.sh com.dexels.navajo.tipi.dev.plugin 1.1.1
#tosnap.sh com.dexels.navajo.tipi.server