#!/bin/bash

function evil_git_dirty {
  [[ $(git diff --shortstat 2> /dev/null | tail -n1) != "" ]] && echo "*"
}

function evil_git_num_untracked_files {
  expr `git status --porcelain 2>/dev/null| grep "^??" | wc -l`
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

if [ evil_git_dirty == "*" ]
then
        echo "Dirty files - Please commit before releasing!"
        exit 1
fi
if [ evil_git_num_untracked_files != 0 ]
then
        echo "Untracked files - Please commit before releasing!"
        exit 1
fi

mvn install
if [ $? -ne 0 ]
then
	echo "Building failed - stopping release!"
	exit 1
fi


VERSION=`cat META-INF/MANIFEST.MF | grep Bundle-Version | awk '{ print $2 }'`
VERSION=${VERSION%.qualifier}
BASEVERSION=`echo "$VERSION" | cut -f1-2 -d '.'`
MINORVERSION=`echo "$VERSION" | cut -f3 -d '.'`
NEWMINOR1=`expr $MINORVERSION + 1`
NEWMINOR1=$BASEVERSION.$NEWMINOR1

echo "Current version: $VERSION"
read -p "Enter release version [$NEWMINOR1]: " TMPNEWMINOR1
NEWMINOR1=${TMPNEWMINOR1:-$NEWMINOR1}
BASEVERSION=`echo "$NEWMINOR1" | cut -f1-2 -d '.'`
NEWMINOR2=$BASEVERSION.$((`echo "$NEWMINOR1"| cut -f3 -d '.'` + 1))
echo "Going to release $NEWMINOR1 and $NEWMINOR2 - press ctrl+c to cancel within 5 seconds"
prettysleep 5

`release.sh $NEWMINOR1 $NEWMINOR2`
