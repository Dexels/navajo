#!/bin/bash

function git_num_files {
  expr `git status --porcelain 2>/dev/null| grep -v '??' |wc -l`
}

function prettysleep {
    current=0;
    while [ $current -lt $1 ]
    do
        string=""
        for i in $(seq 0 $current); do string="$string."; done
        echo -ne "$string\r"
        current=$(( $current + 1 ))
        sleep 1
    done
    echo -ne '\n'
}

dirty=$(git_num_files)
if [ $dirty -gt 0 ]
then
    echo "$dirty dirty files - Please commit before releasing!"
    exit 1
fi

## TODO Only allow release from master

echo "Git pulling..."
git pull

BUNDLENAME=`cat META-INF/MANIFEST.MF | grep Bundle-SymbolicName | awk '{ print $2 }'`
VERSION=`cat META-INF/MANIFEST.MF | grep Bundle-Version | awk '{ print $2 }'`
VERSION=${VERSION%.qualifier}
BASEVERSION=`echo "$VERSION" | cut -f1-2 -d '.'`
MINORVERSION=`echo "$VERSION" | cut -f3 -d '.'`
NEWMINOR1=$MINORVERSION
NEWMINOR1=$BASEVERSION.$NEWMINOR1

echo "Current version: $VERSION"
read -p "Enter release version [$NEWMINOR1]: " TMPNEWMINOR1
NEWMINOR1=${TMPNEWMINOR1:-$NEWMINOR1}
BASEVERSION=`echo "$NEWMINOR1" | cut -f1-2 -d '.'`
NEWMINOR2=$BASEVERSION.$((`echo "$NEWMINOR1"| cut -f3 -d '.'` + 1))

#Check if this new version already exists
GROUPURL=''
GROUPID=`grep -m 1 groupId pom.xml  | cut -f2 -d">"|cut -f1 -d"<"`
GROUPARR=(${GROUPID//\./ })
for i in ${GROUPARR[@]}; do
    GROUPURL="$GROUPURL/$i"
done
STATUS=`curl  -s -w "%{http_code}" "http://repo.dexels.com/nexus/service/local/repositories/navajo/content$GROUPURL/$BUNDLENAME/$NEWMINOR1/$BUNDLENAME-$NEWMINOR1.jar" -o /dev/null`
if [ $STATUS -eq "200" ]
then
    echo "$BUNDLENAME version $NEWMINOR1 already exists - exiting!"
    exit 1
fi

echo "Going to release $BUNDLENAME $NEWMINOR - press ctrl+c to cancel within 5 seconds"
echo "SETREPO: ${SETREPO}"
prettysleep 5

mvn install -DskipTests -Denvironment=release $SETREPO
if [ $? -ne 0 ]
then
	echo "Building failed - stopping release!"
	exit 1
fi

$(dirname $0)/release.sh $NEWMINOR1 $NEWMINOR2

if [ $? -ne 0 ]
then
        echo "Something went wrong!"
        exit 1
fi
echo ""
echo ""
echo "Released $BUNDLENAME $NEWMINOR1"
