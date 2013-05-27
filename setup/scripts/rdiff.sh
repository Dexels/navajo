#!/bin/bash
BASE=$(basename $1)
echo "Rdiffing base: ${BASE}"
version=$(cat $1/META-INF/MANIFEST.MF | grep Bundle-Version | xargs -I {} lastversion.sh {})
echo $version
echo Release_${BASE}-$version
git diff Release_${BASE}-$version --stat $1/src
