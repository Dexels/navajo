#!/bin/bash
#IFS=. read -a v <<<"$versionLine"
versionLine=$1
#echo "Line: $versionLine"
#versionLine=$1 #'Bundle-Version: 1.0.24.qualifier'
cleanVersion="${versionLine##*: }"
#echo "Parsed version: $cleanVersion"

IFS=. read -a v <<<"$cleanVersion"
major=${v[0]}
minor=${v[1]}
micro=${v[2]}
#echo "Major: $major"
#echo "Minor: $minor"
#echo "Micro: $micro"
nextMicro=$(($micro+1))
echo "$major.$minor.$nextMicro"
#printf '%s\n' "${v[@]}"
