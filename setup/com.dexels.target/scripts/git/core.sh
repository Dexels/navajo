export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh DexelsVersionControl com.dexels.navajo.version
./cvsgit.sh NavajoDocument com.dexels.navajo.document
./cvsgit.sh NavajoClient com.dexels.navajo.client
./cvsgit.sh com.dexels.navajo.core.feature com.dexels.navajo.core.feature 
./cvsgit.sh Navajo com.dexels.navajo.core
./cvsgit.sh NavajoAsyncClient com.dexels.navajo.client.async
./cvsgit.sh NavajoFunctions com.dexels.navajo.function
