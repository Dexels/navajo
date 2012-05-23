rsync -av /var/www/html/Tipi/wiki/ root@srv2.dexels.com:/home/navajo/domains/navajo.dexels.com/public_html/SpiritusMirror; 
ssh root@srv2.dexels.com "cd /home/navajo/domains/navajo.dexels.com/public_html/SpiritusMirror;
chown -R apache.apache *; 
echo . >BEWARE_MIRRORED_FOLDER_DO_NOT_EDIT"
rsync -av --exclude 'wiki/data/cache'  --exclude 'AppStore' /var/www/html/Tipi/ root@srv2.dexels.com:/home/navajo/domains/navajo.dexels.com/public_html/TipiRepository; 
ssh root@srv2.dexels.com "cd /home/navajo/domains/navajo.dexels.com/public_html/TipiRepository;
chown -R apache.apache *; 
echo . >BEWARE_MIRRORED_FOLDER_DO_NOT_EDIT"
rsync -av /var/www/html/Navajo/downloads/ root@srv2.dexels.com:/home/navajo/domains/navajo.dexels.com/public_html/downloads; 
ssh root@srv2.dexels.com "cd /home/navajo/domains/navajo.dexels.com/public_html/downloads;
chown -R apache.apache *; 
echo . >BEWARE_MIRRORED_FOLDER_DO_NOT_EDIT"
rsync -av /var/www/html/p2/ root@srv2.dexels.com:/home/navajo/domains/navajo.dexels.com/public_html/p2; 
ssh root@srv2.dexels.com "cd /home/navajo/domains/navajo.dexels.com/public_html/p2;
chown -R apache.apache *; 
echo . >BEWARE_MIRRORED_FOLDER_DO_NOT_EDIT"
