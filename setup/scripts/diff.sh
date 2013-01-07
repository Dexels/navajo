#!/bin/bash
echo "Current version:"
cat META-INF/MANIFEST.MF | grep Bundle-Version
echo Release_${PWD##*/}-$1
git diff Release_${PWD##*/}-$1 --stat .
