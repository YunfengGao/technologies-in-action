# Docker

## Demo运行
### 方法一：
1. 使用Maven打包出jar包
2. 将jar包和Dockerfile放在一台有Docker的机器上
3. `docker build -t japp .`
4. `docker run -d -p 8080:8080 -it --rm japp`
5. 浏览器访问 http://192.168.56.150:8080/health

### 方法二：
1. 添加dockerfile-maven-plugin插件
2. 找一台安装了Maven和Docker的机器
3. `mvn clean package`
4. `docker run -d -p 8080:8080 -it --rm japp:1.0-SNAPSHOT`
5. 浏览器访问 http://localhost:8080/health


### Windows Docker端口占用问题
```shell script
# https://github.com/docker/for-win/issues/3171
netsh int ipv4 show dynamicport tcp
net stop winnat
netsh int ipv4 set dynamic tcp start=49152 num=16384
net start winnat
```