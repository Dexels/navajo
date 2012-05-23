export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh com.dexels.target com.dexels.target
./cvsgit.sh com.dexels.repository com.dexels.repository
./cvsgit.sh com.dexels.opensource com.dexels.opensource

# Decide on this bugger: Where to store the jars?
./cvsgit.sh com.dexels.thirdparty.feature com.dexels.thirdparty.feature
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh NavajoUtilities com.dexels.navajo.utilities
./cvsgit.sh com.dexels.navajo.birt.oda.feature com.dexels.navajo.oda.feature
./cvsgit.sh com.dexels.navajo.oda com.dexels.navajo.oda
./cvsgit.sh com.dexels.navajo.oda.ui com.dexels.navajo.oda.ui

./cvsgit.sh ImageProvider com.dexels.geospatial.imageprovider

./cvsgit.sh RDFBrowser com.dexels.navajo.tipi.swing.rdf
./cvsgit.sh Ticketing com.dexels.demos.ticketing

./cvsgit.sh PeelPage com.dexels.demos.peelpage
./cvsgit.sh PinkRoccadeAdapters com.dexels.demos.pinkroccade.adapters
./cvsgit.sh PinkRoccadeEI com.dexels.demos.pinkroccade.ei
./cvsgit.sh SMSGateway com.dexels.utilities.smsgateway
./cvsgit.sh StadiumDesigner com.dexels.demos.stadiumdesigner
./cvsgit.sh TipiDashboard com.dexels.demos.tipidashboard


#other languages


#internal-repo docs (MERGE!)
./cvsgit.sh Dexels com.dexels.documents
./cvsgit.sh Papers com.dexels.papers
./cvsgit.sh DexelsDocs com.dexels.documents.other
./cvsgit.sh KNVBTicketing com.dexels.documents.ticketing




#sportlink-repo

./cvsgit.sh KNHB-Sporttaal com.sportlink.knhb.sporttaal

./cvsgit.sh KNZB-JSP-Interface com.sportlink.knzb.interface

./cvsgit.sh MDex com.sportlink.james

#cocoa
./cvsgit.sh NavajoCocoaClient com.dexels.navajo.cocoa.client
./cvsgit.sh SportlinkDwfTouch com.sportlink.dwf.bv.ios

#php
./cvsgit.sh KNVBWidget com.sportlink.knvb.widget
# don't know the respective roles of these two projects:
./cvsgit.sh NavajoPhp com.dexels.navajo.client.php
./cvsgit.sh NavajoPhpLibrary com.dexels.navajo.client.php.library
./cvsgit.sh NavajoJoomlaPlugin com.dexels.navajo.php.joomla

#joomla
./cvsgit.sh DexDMS com.sportlink.dms.joomla
#laszlo: NOTE: DWF (another very similar project) is obsolete
./cvsgit.sh sportlink-dwf-bv com.sportlink.dwf.bv.web
#java
./cvsgit.sh AutomatischePouleIndeling com.sportlink.optimizepool
#  client, in tipi
./cvsgit.sh DocumentManagement com.sportlink.dms.client
# also: SportlinkDMS, seems very old
#jsp

./cvsgit.sh GeoJsp com.sportlink.geo.jsp
./cvsgit.sh Geo com.sportlink.geo


./cvsgit.sh sportlink-downloadcentre com.sportlink.downloadservlet
./cvsgit.sh sportlink-knvb-digitaalinschrijven com.sportlink.digitaalinschrijven

./cvsgit.sh AccommodatieZaken com.sportlink.accommodatiezaken
./cvsgit.sh SportlinkClub com.sportlink.club

./cvsgit.sh TensingOracle com.sportlink.tensing

./cvsgit.sh SportlinkOfficialPortal com.sportlink.officialportal
./cvsgit.sh NavajoSwingClient com.sportlink.swing.client


./cvsgit.sh sportlink-serv com.sportlink.serv
./cvsgit.sh TipiFacilityOccpation com.sportlink.tipi.facilityoccupation
./cvsgit.sh sportlink-clnt-v2 com.sportlink.client
./cvsgit.sh sportlink-adapters com.sportlink.adapters
./cvsgit.sh sportlink-serv-crystal com.sportlink.crystal
./cvsgit.sh sportlink-serv-AAA com.sportlink.aaa
./cvsgit.sh sportlink-serv-comp com.sportlink.comp
./cvsgit.sh SportlinkMatchForms com.sportlink.matchforms
./cvsgit.sh SportlinkBackoffice com.sportlink.backoffice
./cvsgit.sh GoogleEarthAccommodations com.sportlink.accommodations
./cvsgit.sh SportlinkCIF com.sportlink.cif
./cvsgit.sh NevoboDPS com.sportlink.nevobo.dps
./cvsgit.sh SportlinkDWF com.sportlink.dwf.av
./cvsgit.sh DWFDashboard com.sportlink.dwf.dashboard
./cvsgit.sh SportlinkDashboard com.sportlink.dashboard
./cvsgit.sh SportlinkReports com.sportlink.reports
./cvsgit.sh SportlinkTeamRegistration com.sportlink.teamregistration
./cvsgit.sh SportlinkWebshop com.sportlink.webshop

./cvsgit.sh sportlink-dba com.sportlink.dba
./cvsgit.sh sportlink-analysis com.sportlink.analysis




./cvsgit.sh TipiLessonOne tipi.lesson.one
./cvsgit.sh TipiLessonTwo tipi.lesson.two
./cvsgit.sh TipiLessonThree tipi.lesson.three
./cvsgit.sh TipiLessonFour tipi.lesson.four
./cvsgit.sh TipiLessonFive tipi.lesson.five
./cvsgit.sh TipiLessonSix tipi.lesson.six
./cvsgit.sh TwitterAdapter com.dexels.navajo.twitter
./cvsgit.sh com.dexels.navajo.twitter.feature com.dexels.navajo.twitter.feature
./cvsgit.sh NavajoSvgRenderAdapter com.dexels.navajo.svg
./cvsgit.sh NavajoGeoTools com.dexels.navajo.geo 
./cvsgit.sh NavajoKmlAdapter com.dexels.navajo.kml
./cvsgit.sh com.dexels.navajo.geo.feature com.dexels.navajo.geo.feature
./cvsgit.sh com.dexels.navajo.workflow.editor com.dexels.navajo.workflow.editor
./cvsgit.sh com.dexels.navajo.workflow.editor.gmf com.dexels.navajo.workflow.editor.gmf
./cvsgit.sh com.dexels.navajo.editor.feature com.dexels.navajo.workflow.editor.feature
./cvsgit.sh NavajoPDF com.dexels.navajo.function.pdf
./cvsgit.sh Lucene com.dexels.navajo.enterprise.lucene
./cvsgit.sh FileUploadServlet com.dexels.navajo.fileuploadservlet
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh DexelsVersionControl com.dexels.navajo.version
./cvsgit.sh NavajoDocument com.dexels.navajo.document
./cvsgit.sh NavajoClient com.dexels.navajo.client
./cvsgit.sh com.dexels.navajo.core.feature com.dexels.navajo.core.feature 
./cvsgit.sh Navajo com.dexels.navajo.core
./cvsgit.sh NavajoAsyncClient com.dexels.navajo.client.async
./cvsgit.sh NavajoFunctions com.dexels.navajo.function
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh com.dexels.navajo.server.feature com.dexels.navajo.server.feature
./cvsgit.sh com.dexels.navajo.server.embedded.feature com.dexels.navajo.server.embedded.feature
./cvsgit.sh NavajoQueueManager com.dexels.navajo.queuemanager
./cvsgit.sh NavajoListeners com.dexels.navajo.listeners 
./cvsgit.sh NavajoListenersContinuations com.dexels.navajo.listeners.continuations
./cvsgit.sh NavajoAdapters com.dexels.navajo.adapters 
./cvsgit.sh NavajoRhino com.dexels.navajo.rhino
./cvsgit.sh com.dexels.navajo.rhino.continuations com.dexels.navajo.rhino.continuations
./cvsgit.sh com.dexels.navajo.authentication.api com.dexels.navajo.authentication.api
./cvsgit.sh NavajoServer com.dexels.navajo.server.deploy

./cvsgit.sh NavajoAgents com.dexels.navajo.agents
./cvsgit.sh NavajoDBReplicationService com.dexels.navajo.enterprise.dbreplication

# still clean up these:
./cvsgit.sh NavajoDashboardTwo com.dexels.navajo.dashboard
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
#dev
./cvsgit.sh NavajoBIRT com.dexels.navajo.birt.push 
./cvsgit.sh NavajoJspServer com.dexels.navajo.jsp.server 
./cvsgit.sh NavajoJsp com.dexels.navajo.jsp 
./cvsgit.sh com.dexels.navajo.tipi.swt.client com.dexels.navajo.tipi.swt.client 
./cvsgit.sh com.dexels.navajo.dev com.dexels.navajo.dev.feature 
./cvsgit.sh NavajoScriptPlugin com.dexels.navajo.dev.script 
./cvsgit.sh com.dexels.navajo.server.embedded com.dexels.navajo.server.embedded
./cvsgit.sh com.dexels.navajo.dsl.navajomanager com.dexels.navajo.dsl.navajomanager
./cvsgit.sh com.dexels.navajo.dsl.expression com.dexels.navajo.dsl.expression
./cvsgit.sh com.dexels.navajo.dsl.expression.model com.dexels.navajo.dsl.expression.model
./cvsgit.sh com.dexels.navajo.dsl.expression.ui com.dexels.navajo.dsl.expression.ui
./cvsgit.sh com.dexels.navajo.dsl.tsl com.dexels.navajo.dsl.tsl
./cvsgit.sh com.dexels.navajo.dsl.tsl.model com.dexels.navajo.dsl.tsl.model
./cvsgit.sh com.dexels.navajo.dsl.tsl.ui com.dexels.navajo.dsl.tsl.ui
./cvsgit.sh com.dexels.navajo.dsl.dev.integration com.dexels.navajo.dsl.integration
./cvsgit.sh com.dexels.navajo.language.feature com.dexels.navajo.dsl.feature
./cvsgit.sh NavajoRemoteTestLibrary com.dexels.navajo.remotetest


./cvsgit.sh NavaDoc com.dexels.navajo.dev.navadoc 
./cvsgit.sh NavaTest com.dexels.navajo.dev.navajo.test 

export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
#enterprise
./cvsgit.sh com.dexels.navajo.tools.wsdl com.dexels.navajo.wsdl
./cvsgit.sh com.dexels.navajo.wsdl.feature com.dexels.navajo.wsdl.feature
./cvsgit.sh com.dexels.navajo.server.enterprise.feature com.dexels.navajo.enterprise.feature
./cvsgit.sh NavajoEnterpriseListeners com.dexels.navajo.enterprise.listeners
./cvsgit.sh NavajoEnterpriseAdapters com.dexels.navajo.enterprise.adapters
./cvsgit.sh NavajoEnterprise com.dexels.navajo.enterprise
./cvsgit.sh com.dexels.navajo.enterprise.listeners.deps com.dexels.navajo.enterprise.listeners.deps
./cvsgit.sh com.dexels.navajo.enterprise.adapters.deps com.dexels.navajo.enterprise.adapters.deps
./cvsgit.sh com.dexels.navajo.mongo.feature com.dexels.navajo.mongo.feature
./cvsgit.sh com.dexels.navajo.mongo com.dexels.navajo.mongo
./cvsgit.sh com.dexels.navajo.mongo.navajostore com.dexels.navajo.mongo.navajostore
./cvsgit.sh com.dexels.navajo.other.feature com.dexels.navajo.other.feature
./cvsgit.sh com.dexels.navajo.test.feature com.dexels.navajo.test.feature
./cvsgit.sh NavajoRemoteTestLibrary com.dexels.navajo.test.remote
./cvsgit.sh com.dexels.navajo.server.bridged com.dexels.navajo.server.bridged
./cvsgit.sh com.dexels.navajo.server.bridged.deploy com.dexels.navajo.server.bridged.deploy

./cvsgit.sh NavajoIdeal com.dexels.navajo.enterprise.ideal
./cvsgit.sh sportlink-ideal com.dexels.navajo.enterprise.ideal.sportlink
./cvsgit.sh OpenfireNavajo com.dexels.navajo.enterprise.openfire
./cvsgit.sh NavajoTomahawk com.dexels.navajo.enterprise.tomahawk

./cvsgit.sh NavajoAgents com.dexels.navajo.enterprise.agents

#NavajoIntegrationTest

export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh NavajoTipi com.dexels.navajo.tipi
./cvsgit.sh com.dexels.navajo.tipi.feature com.dexels.navajo.tipi.feature
./cvsgit.sh com.dexels.navajo.tipi.swing.feature com.dexels.navajo.tipi.swing.feature
./cvsgit.sh TipiSwingClient com.dexels.navajo.tipi.swing.client
./cvsgit.sh NavajoSwingTipi com.dexels.navajo.tipi.swing
./cvsgit.sh com.dexels.navajo.tipi.swing.deps com.dexels.navajo.tipi.swing.deps
./cvsgit.sh com.dexels.navajo.tipi.swing.application com.dexels.navajo.tipi.swing.application
./cvsgit.sh NavajoEchoTipi com.dexels.navajo.tipi.echo
./cvsgit.sh NavajoEchoClient com.dexels.navajo.tipi.echo.client
./cvsgit.sh com.dexels.navajo.tipi.echo.feature com.dexels.navajo.tipi.echo.feature
./cvsgit.sh com.dexels.navajo.tipi.vaadin com.dexels.navajo.tipi.vaadin
./cvsgit.sh com.dexels.navajo.tipi.vaadin.bridged com.dexels.navajo.tipi.vaadin.bridged
./cvsgit.sh com.dexels.navajo.tipi.vaadin.embedded com.dexels.navajo.tipi.vaadin.embedded
./cvsgit.sh com.dexels.navajo.tipi.vaadin.bridged.deploy com.dexels.navajo.tipi.vaadin.bridged.deploy
./cvsgit.sh com.dexels.navajo.tipi.vaadin.feature com.dexels.navajo.tipi.vaadin.feature
./cvsgit.sh com.dexels.navajo.tipi.jogl com.dexels.navajo.tipi.jogl
./cvsgit.sh com.dexels.navajo.tipi.extensions.feature com.dexels.navajo.tipi.extensions.feature
./cvsgit.sh com.dexels.navajo.tipi.css com.dexels.navajo.tipi.css
./cvsgit.sh com.dexels.navajo.tipi.css.tkui com.dexels.navajo.tipi.css.tkui
./cvsgit.sh com.dexels.navajo.tipi.css.feature com.dexels.navajo.tipi.css.feature
./cvsgit.sh RDFBrowser com.dexels.navajo.tipi.swing.rdf
./cvsgit.sh com.dexels.navajo.tipi.swing.rdf.feature com.dexels.navajo.tipi.swing.rdf.feature
./cvsgit.sh com.dexels.navajo.tipi.css.feature com.dexels.navajo.tipi.css.feature
export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh TipiBuildLibrary com.dexels.navajo.tipi.dev.core
./cvsgit.sh TipiAntBuild com.dexels.navajo.tipi.dev.ant
./cvsgit.sh TipiPlugin com.dexels.navajo.tipi.dev.plugin
./cvsgit.sh com.dexels.navajo.tipi.dev.feature com.dexels.navajo.tipi.dev.feature
./cvsgit.sh TipiBuild com.dexels.navajo.tipi.build
./cvsgit.sh TipiServer com.dexels.navajo.tipi.server

