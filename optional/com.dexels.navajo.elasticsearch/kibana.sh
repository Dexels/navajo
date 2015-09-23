docker run --rm --name kibana -p 5601:5601 -p 9201:9293 -e ELASTICSEARCH_URL="http://192.168.99.101:9200" kibana:4.1.1

