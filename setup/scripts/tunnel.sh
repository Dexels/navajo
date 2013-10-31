#!/bin/bash
#ssh -L 0.0.0.0:21521:source.dexels.com:1521 -N openshift@source.dexels.com
ssh -L 0.0.0.0:$1:$2 -N openshift@source.dexels.com
