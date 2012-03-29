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

