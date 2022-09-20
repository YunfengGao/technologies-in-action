docker pull docker.io/elasticsearch:8.4.1
docker network create es-network
# 使用用户elastic和密码temp 访问 https://localhost:9200/
docker run -d --name elasticsearch --net es-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "ELASTIC_PASSWORD=temp" elasticsearch:8.4.1
