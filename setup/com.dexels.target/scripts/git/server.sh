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
