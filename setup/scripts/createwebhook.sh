token=$1
repository=sportlink
url=$2
curl -i -H "Authorization: token $token"  -d '{"name":"web", "active":true,"config": {"url":"'"$url"'","content_type":"json"}}' -i https://api.github.com/repos/Dexels/${repository}/hooks


