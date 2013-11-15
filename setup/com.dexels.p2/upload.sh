#!/bin/bash
scp target/com.dexels.navajo.repository-1.0.0.zip navajo@repo.dexels.com:/var/www/html/repo/eclipse/navajo_snapshot/
ssh navajo@repo.dexels.com "cd /var/www/html/repo/eclipse/navajo_snapshot/; rm -rf *.jar META-INF/ plugins/ features/; unzip com.dexels.navajo.repository-1.0.0.zip; rm -f com.dexels.navajo.repository-1.0.0.zip"

