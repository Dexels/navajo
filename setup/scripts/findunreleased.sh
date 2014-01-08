#!/bin/bash
find . -name 'com.dexels.navajo.*' -type d -exec rdiff.sh {} \;
find . -name 'com.sportlink.*' -type d -exec rdiff.sh {} \;
