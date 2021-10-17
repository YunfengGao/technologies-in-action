# Nginx安装启动

## Nginx官网
https://nginx.org/

## 启动nginx
```shell script
# https://nginx.org/en/docs/beginners_guide.html

# 启动
nginx

# 查看nginx进程
ps -ax | grep nginx

# 直接通过宿主机浏览器访问 192.168.56.110
# Welcome to nginx!

# 快速停止
nginx -s stop

# 优雅停止
nginx -s quit
```

## 常用命令
```shell script
# 查看nginx已安装的模块
nginx -V

# 重新加载配置文件
nginx -s reload
```

