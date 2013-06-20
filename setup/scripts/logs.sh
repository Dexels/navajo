#/bin/bash
git log --pretty=format:%s "Release_${PWD##*/}-$1"..HEAD .

