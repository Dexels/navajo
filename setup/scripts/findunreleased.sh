#!/bin/bash
find . -name 'com.dexels.*' -type d -exec rdiff.sh {} \;
find . -name 'com.sportlink.*' -type d -exec rdiff.sh {} \;
