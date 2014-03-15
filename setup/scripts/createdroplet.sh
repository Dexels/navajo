client_id=$1
api_key=$2
name=$3
image=fedora-19-x64
region=ams2
size=512mb 
sshkey=67021,95246 #frank, butler

curl "https://api.digitalocean.com/droplets/new?client_id=${client_id}&api_key=${api_key}&name=${name}&size_slug=${size}&region_slug=${region}&ssh_key_ids=${sshkey}&image_slug=${image}"

