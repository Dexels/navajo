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

./cvsgit.sh NavajoAgents com.dexels.navajo.agents

#NavajoIntegrationTest

