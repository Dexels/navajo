######## ORIGINAL START

#Global
rm -rf /home/mercurial/com.dexels.repository /home/mercurial/com.dexels.target
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --fallback-encoding=ascii --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.target /home/cvs/com.dexels.target
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.repository /home/cvs/com.dexels.repository

#Core
rm -rf /home/mercurial/com.dexels.navajo.core.feature /home/mercurial/com.dexels.navajo.client.deps /home/mercurial/com.dexels.navajo.version  /home/mercurial/com.dexels.navajo.document /home/mercurial/com.dexels.navajo.client /home/mercurial/com.dexels.navajo.core /home/mercurial/com.dexels.navajo.function /home/mercurial/com.dexels.navajo.client.async
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.core.feature /home/cvs/com.dexels.navajo.core.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.client.deps /home/cvs/com.dexels.navajo.client.deps

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.version /home/cvs/DexelsVersionControl
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.document /home/cvs/NavajoDocument
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.client /home/cvs/NavajoClient
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.core /home/cvs/Navajo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.function /home/cvs/NavajoFunctions
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.client.async /home/cvs/NavajoAsyncClient


# Server
rm -rf /home/mercurial/com.dexels.navajo.server.feature /home/mercurial/com.dexels.navajo.server.embedded.feature /home/mercurial/com.dexels.navajo.queuemanager /home/mercurial/com.dexels.navajo.listeners /home/mercurial/com.dexels.navajo.listeners.continuations /home/mercurial/com.dexels.navajo.adapters /home/mercurial/com.dexels.navajo.rhino /home/mercurial/com.dexels.navajo.jsp /home/mercurial/com.dexels.navajo.jsp.server
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.feature /home/cvs/com.dexels.navajo.server.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.embedded.feature /home/cvs/com.dexels.navajo.server.embedded.feature

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.queuemanager /home/cvs/NavajoQueueManager
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.listeners /home/cvs/NavajoListeners
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.listeners.continuations /home/cvs/NavajoListenersContinuations
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.adapters /home/cvs/NavajoAdapters
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.rhino /home/cvs/NavajoRhino
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.jsp /home/cvs/NavajoJsp
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.jsp.server /home/cvs/NavajoJspServer

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.deploy /home/cvs/NavajoServer


#Dev
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.birt.push /home/cvs/NavajoBIRT
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swt.client /home/cvs/com.dexels.navajo.tipi.swt.client
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dev.feature /home/cvs/com.dexels.navajo.dev
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dev.script /home/cvs/NavajoScriptPlugin
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.embedded /home/cvs/com.dexels.navajo.server.embedded
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.navajomanager /home/cvs/com.dexels.navajo.dsl.navajomanager
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.expression /home/cvs/com.dexels.navajo.dsl.expression
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.expression.model /home/cvs/com.dexels.navajo.dsl.expression.model
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.expression.ui /home/cvs/com.dexels.navajo.dsl.expression.ui
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.tsl /home/cvs/com.dexels.navajo.dsl.tsl
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.tsl.model /home/cvs/com.dexels.navajo.dsl.tsl.model
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.tsl.ui /home/cvs/com.dexels.navajo.dsl.tsl.ui

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.integration /home/cvs/com.dexels.navajo.dsl.dev.integration

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.dsl.feature /home/cvs/com.dexels.navajo.language.feature

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.remotetest /home/cvs/NavajoRemoteTestLibrary
# remote test feature?


#Wsdl
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.wsdl /home/cvs/com.dexels.navajo.tools.wsdl
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.wsdl.feature /home/cvs/com.dexels.navajo.wsdl.feature


#Enterprise


#cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/org.jgroups /home/cvs/org.jgroups
#cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/org.kjkoster.zapcat /home/cvs/org.kjkoster.zapcat
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise.feature /home/cvs/com.dexels.navajo.server.enterprise.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise.listeners /home/cvs/NavajoEnterpriseListeners
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise.adapters /home/cvs/NavajoEnterpriseAdapters
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise /home/cvs/NavajoEnterprise
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise.listeners.deps /home/cvs/com.dexels.navajo.enterprise.listeners.deps
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.enterprise.adapters.deps /home/cvs/com.dexels.navajo.enterprise.adapters.deps

#Mongo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.mongo.feature /home/cvs/com.dexels.navajo.mongo.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.mongo /home/cvs/com.dexels.navajo.mongo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.mongo.navajostore /home/cvs/com.dexels.navajo.mongo.navajostore

#Navajo Other
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.other.feature /home/cvs/com.dexels.navajo.other.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.other.utilities /home/cvs/NavajoUtilities

#Resource
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.feature /home/cvs/com.dexels.navajo.resource.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource /home/cvs/com.dexels.navajo.resource
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.jdbc.h2 /home/cvs/com.dexels.navajo.resource.jdbc.h2
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.jdbc.mysql /home/cvs/com.dexels.navajo.resource.jdbc.mysql
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.jdbc.oracle /home/cvs/com.dexels.navajo.resource.jdbc.oracle
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.mongodb /home/cvs/com.dexels.navajo.resource.mongodb
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.test /home/cvs/com.dexels.navajo.resource.test
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.resource.manager /home/cvs/com.dexels.navajo.resource.manager


#Navajo Test
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.test.feature /home/cvs/com.dexels.navajo.test.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.test.remote /home/cvs/NavajoRemoteTestLibrary

#Navajo Deploy
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server /home/cvs/NavajoServer
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.bridged /home/cvs/com.dexels.navajo.server.bridged
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.server.bridged.deploy /home/cvs/com.dexels.navajo.server.bridged.deploy



# Tipi Core
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi /home/cvs/NavajoTipi
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.feature /home/cvs/com.dexels.navajo.tipi.feature

# Tipi Swing
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.feature /home/cvs/com.dexels.navajo.tipi.swing.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.client /home/cvs/TipiSwingClient
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing /home/cvs/NavajoSwingTipi
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.deps /home/cvs/com.dexels.navajo.tipi.swing.deps
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.application /home/cvs/com.dexels.navajo.tipi.swing.application


# Tipi Echo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.echo /home/cvs/NavajoEchoTipi
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.echo.client /home/cvs/NavajoEchoClient
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.echo.feature /home/cvs/com.dexels.navajo.tipi.echo.feature



#Tipi Vaadin
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.vaadin /home/cvs/com.dexels.navajo.tipi.vaadin
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.vaadin.bridged /home/cvs/com.dexels.navajo.tipi.vaadin.bridged
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.vaadin.embedded /home/cvs/com.dexels.navajo.tipi.vaadin.embedded
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.vaadin.bridged.deploy /home/cvs/com.dexels.navajo.tipi.vaadin.bridged.deploy
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.vaadin.feature /home/cvs/com.dexels.navajo.tipi.vaadin.feature


# Tipi Swing Optional
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.docking.feature /home/cvs/com.dexels.navajo.tipi.swing.docking.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.docking /home/cvs/TipiSwingDocking
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.charting.feature /home/cvs/com.dexels.navajo.tipi.swing.charting.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.charting /home/cvs/TipiCharting

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.editor.feature /home/cvs/com.dexels.navajo.tipi.swing.editor.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.editor /home/cvs/TipiSwingEditor
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.swing.editor /home/cvs/NavajoSwingEditor

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.svg.feature /home/cvs/com.dexels.navajo.tipi.swing.svg.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.svg /home/cvs/TipiSvgBatik

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.mig.feature /home/cvs/com.dexels.navajo.tipi.swing.mig.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.mig /home/cvs/TipiSwingMig

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.substance.feature /home/cvs/com.dexels.navajo.tipi.swing.substance.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.substance /home/cvs/TipiSubstance

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.geo.feature /home/cvs/com.dexels.navajo.tipi.swing.geo.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.geo /home/cvs/TipiGeoSwing
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.swingx /home/cvs/TipiSwingX
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.jxlayer /home/cvs/TipiJxLayer


cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.rich.feature /home/cvs/com.dexels.navajo.tipi.swing.rich.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.rich.client /home/cvs/NavajoRichClient
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.rich /home/cvs/NavajoRichTipi


cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.cobra.feature /home/cvs/com.dexels.navajo.tipi.swing.cobra.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.swing.cobra /home/cvs/TipiCobraBrowser

# Tipi Optional

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.flickr.feature /home/cvs/com.dexels.navajo.tipi.flickr.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.flickr /home/cvs/TipiFlickr
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.mail.feature /home/cvs/com.dexels.navajo.tipi.mail.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.mail /home/cvs/TipiMail
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.jabber.feature /home/cvs/com.dexels.navajo.tipi.jabber.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.jabber /home/cvs/TipiJabber

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.ruby.feature /home/cvs/com.dexels.navajo.tipi.ruby.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.ruby /home/cvs/TipiRuby

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.rss.feature /home/cvs/com.dexels.navajo.tipi.rss.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.rss /home/cvs/TipiRss


#TipiDev
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.dev.core /home/cvs/TipiBuildLibrary
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.dev.ant /home/cvs/TipiAntBuild
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.dev.plugin /home/cvs/TipiPlugin
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.dev.feature /home/cvs/com.dexels.navajo.tipi.dev.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.build /home/cvs/TipiBuild
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.tipi.server /home/cvs/TipiServer

#Sportlink/Tipi
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.tipi.facilityoccupation /home/cvs/TipiFacilityOccpation


#Other
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.utilities /home/cvs/NavajoUtilities


#Oda driver
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.oda.feature /home/cvs/com.dexels.navajo.birt.oda.feature
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.oda /home/cvs/com.dexels.navajo.oda
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.oda.ui /home/cvs/com.dexels.navajo.oda.ui


#Legacy
#sportlink client
#sportlink serv

#cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.serv /home/cvs/sportlink-serv

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.client /home/cvs/sportlink-clnt-v2
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.adapters /home/cvs/sportlink-adapters
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.crystal /home/cvs/sportlink-serv-crystal
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.aaa /home/cvs/sportlink-serv-AAA
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.comp /home/cvs/sportlink-serv-comp
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.tensing /home/cvs/TensingOracle

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.sportlink.club /home/cvs/SportlinkClub
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.sportlink.officialportal /home/cvs/SportlinkOfficialPortal

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.sportlink.swing.client /home/cvs/NavajoSwingClient



#tipi projects (lessons)

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.one /home/cvs/TipiLessonOne
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.two /home/cvs/TipiLessonTwo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.three /home/cvs/TipiLessonThree
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.four /home/cvs/TipiLessonFour
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.five /home/cvs/TipiLessonFive
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.six /home/cvs/TipiLessonSix
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/tipi.lesson.seven /home/cvs/TipiLessonSeven


#Navajo Twitter
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.twitter /home/cvs/TwitterAdapter
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.twitter.feature /home/cvs/com.dexels.navajo.twitter.feature

# Navajo Geo
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.svg /home/cvs/NavajoSvgRenderAdapter
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.geo /home/cvs/NavajoGeoTools
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.kml /home/cvs/NavajoKmlAdapter
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.geo.feature /home/cvs/com.dexels.navajo.geo.feature


cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.workflow.editor /home/cvs/com.dexels.navajo.workflow.editor
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.workflow.editor.gmf /home/cvs/com.dexels.navajo.workflow.editor.gmf
cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.workflow.editor.feature /home/cvs/com.dexels.navajo.workflow.feature

cvs2hg --encoding=utf_8 --fallback-encoding=ascii --hgrepos /home/mercurial/com.dexels.navajo.authentication.api /home/cvs/com.dexels.navajo.authentication.api


