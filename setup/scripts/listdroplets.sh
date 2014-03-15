client_id=$1
api_key=$2
curl "https://api.digitalocean.com/droplets/?client_id=${client_id}&api_key=${api_key}"
curl "https://api.digitalocean.com/ssh_keys/?client_id=${client_id}&api_key=${api_key}"

