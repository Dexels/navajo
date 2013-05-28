#!/bin/bash
find . -name 'com.dexels.navajo.*' -type d -exec rdiff.sh {} \;
