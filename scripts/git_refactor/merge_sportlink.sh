export REPOSITORY=sportlink
export MODULEPATH=libraries


genmerge.sh com.sportlink.tipi.facilityoccupation
genmerge.sh com.sportlink.adapters
genmerge.sh com.sportlink.crystal
genmerge.sh com.sportlink.aaa
genmerge.sh com.sportlink.comp
genmerge.sh com.sportlink.tensing
genmerge.sh com.sportlink.swing.client
genmerge.sh com.sportlink.nevobo.dps
genmerge.sh com.sportlink.reports
genmerge.sh com.sportlink.dba
genmerge.sh com.sportlink.optimizepool
# left it out, needs trimming down (KNHB-Sporttaal)
#genmerge.sh com.sportlink.knhb.sporttaal

export MODULEPATH=applications

genmerge.sh com.sportlink.client
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
genmerge.sh com.sportlink.accommodatiezaken
genmerge.sh com.sportlink.dms.joomla
genmerge.sh com.sportlink.dms.client
genmerge.sh com.sportlink.knvb.widget
genmerge.sh com.sportlink.knzb.interface
genmerge.sh com.sportlink.james
genmerge.sh com.sportlink.dwf.bv.web
genmerge.sh com.sportlink.downloadservlet

export MODULEPATH=documents
genmerge.sh com.sportlink.analysis
genmerge.sh com.sportlink.dba


export REPOSITORY=sportlink_ios
export MODULEPATH=dwf
# maybe separate repo?
genmerge.sh com.sportlink.dwf.bv.ios
