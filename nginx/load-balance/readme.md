# 负载均衡

## nginx.conf 配置
```shell script
http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;

    include /etc/nginx/conf.d/*.conf;
    
    upstream balance_server {
        server 192.168.56.110:8080;
        server 192.168.56.110:8081;
    }
    server {
        listen 80;
        server_name 192.168.56.110;

        location /balance {
            proxy_pass http://balance_server;
        }
    }
}

```

## 启动两个服务器
```shell script
# centos7 自带python2
python --version

mkdir -p /home/8080/balance
echo 8080 > /home/8080/balance/index.html
cd /home/8080/ && python -m SimpleHTTPServer 8080

# 另开一个terminal，启动另一个服务器
mkdir -p /home/8081/balance
echo 8081 > /home/8081/balance/index.html
cd /home/8081/ && python -m SimpleHTTPServer 8081
```

## 测试
在宿主机浏览器连续访问`http://192.168.56.110/balance/`，会依次显示8080/8081
