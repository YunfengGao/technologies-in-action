docker pull docker.io/elasticsearch:8.4.1
docker network create es-network
# 使用用户elastic和密码es-password 访问 https://localhost:9200/
docker run -d --name elasticsearch --net es-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "ELASTIC_PASSWORD=es-password" elasticsearch:8.4.1

# 安装 ik分词器 https://github.com/medcl/elasticsearch-analysis-ik
docker exec -it elasticsearch bash
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.4.1/elasticsearch-analysis-ik-8.4.1.zip
docker restart elasticsearch

