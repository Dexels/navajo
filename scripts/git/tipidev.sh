export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh TipiBuildLibrary com.dexels.navajo.tipi.dev.core
./cvsgit.sh TipiAntBuild com.dexels.navajo.tipi.dev.ant
./cvsgit.sh TipiPlugin com.dexels.navajo.tipi.dev.plugin
./cvsgit.sh com.dexels.navajo.tipi.dev.feature com.dexels.navajo.tipi.dev.feature
./cvsgit.sh TipiBuild com.dexels.navajo.tipi.build
./cvsgit.sh TipiServer com.dexels.navajo.tipi.server
