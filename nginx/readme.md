# Nginx安装启动

## 配置SSH登录
```shell script
vagrant up

# 登录虚拟机并切换root
su root

# 允许root使用ssh登录
sed -i 's/#PermitRootLogin yes/PermitRootLogin yes/' /etc/ssh/sshd_config
# 允许使用密码登录
sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/' /etc/ssh/sshd_config

systemctl restart sshd
```

## 更新源
```shell script
yum install wget

cd /etc/yum.repos.d

mv CentOS-Base.repo CentOS-Base.repo.bk

wget http://mirrors.163.com/.help/CentOS6-Base-163.repo

# 生成缓存
yum makecache
```

## 安装nginx
```shell script
# https://nginx.org/en/linux_packages.html#RHEL-CentOS

yum install yum-utils -y

echo "[nginx-stable]
name=nginx stable repo
baseurl=http://nginx.org/packages/centos/7Server/x86_64/
gpgcheck=1
enabled=1
gpgkey=https://nginx.org/keys/nginx_signing.key
module_hotfixes=true

[nginx-mainline]
name=nginx mainline repo
baseurl=http://nginx.org/packages/mainline/centos/7Server/x86_64/
gpgcheck=1
enabled=0
gpgkey=https://nginx.org/keys/nginx_signing.key
module_hotfixes=true" > /etc/yum.repos.d/nginx.repo

yum list nginx

yum install nginx
```

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

