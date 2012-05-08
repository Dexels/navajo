export DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
./cvsgit.sh com.dexels.target com.dexels.target
./cvsgit.sh com.dexels.repository com.dexels.repository

# Decide on this bugger: Where to store the jars?
./cvsgit.sh com.dexels.thirdparty.feature com.dexels.thirdparty.feature
