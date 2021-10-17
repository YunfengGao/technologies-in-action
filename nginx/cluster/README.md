# nginx集群

使用[keepalived](https://www.keepalived.org/manpage.html) 实现Nginx集群高可用，每个节点都需要安装nginx和keepalived

|节点|ip地址|
|----|----|
|虚拟IP (VIP) | 192.168.56.110|    
|主节点 | 192.168.56.111    |
|备节点1 | 192.168.56.112   |
|备节点2 | 192.168.56.113   |

## 集群创建和环境配置
```shell script
vagrant up
```

## keepalived配置

### nginx检测脚本
```shell script
# 三个节点都需要配置
# /usr/local/bin/check_nginx_is_running.sh
echo '#!/bin/bash
A=`ps -C nginx --noheader |wc -l`
if [ $A -eq 0 ]
then
  nginx
  sleep 1
  if [ `ps -C nginx --noheader |wc -l` -eq 0 ]
  then
    pkill keepalived
  fi
fi' > /usr/local/bin/check_nginx_is_running.sh

# 增加执行权限
chmod +x /usr/local/bin/check_nginx_is_running.sh
```

### 主节点 
```shell script
vi /etc/keepalived/keepalived.conf
# 内容如下
! Configuration File for keepalived

global_defs {
   notification_email {
     acassen@firewall.loc
     failover@firewall.loc
     sysadmin@firewall.loc
   }
   notification_email_from Alexandre.Cassen@firewall.loc
   smtp_server 192.168.200.1
   smtp_connect_timeout 30
   router_id 192.168.56.111
   vrrp_skip_check_adv_addr
   # 注释掉，表示严格执行VRRP协议规范，此模式不支持节点单播
   # vrrp_strict
   vrrp_garp_interval 0
   vrrp_gna_interval 0
}

vrrp_script chk_nginx {
    script "/usr/local/bin/check_nginx_is_running.sh"
    interval 2 # 脚本执行时间，单位秒
    weight 2
}

vrrp_instance VI_1 {
    state MASTER  # 主节点
    interface eth1 # 绑定网卡，根据ip addr查出来
    virtual_router_id 51  # 组队标志，同一个vip下一致
    priority 100 # 越大优先级越高
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.110 # VIP地址，三个节点配成一样的
    }
    track_script {
        chk_nginx
    }
}

# 启动keepalived
systemctl start keepalived
```

### 备节点1 
```shell script
vi /etc/keepalived/keepalived.conf
# 内容如下
! Configuration File for keepalived

global_defs {
   notification_email {
     acassen@firewall.loc
     failover@firewall.loc
     sysadmin@firewall.loc
   }
   notification_email_from Alexandre.Cassen@firewall.loc
   smtp_server 192.168.200.1
   smtp_connect_timeout 30
   router_id 192.168.56.112
   vrrp_skip_check_adv_addr
   # vrrp_strict
   vrrp_garp_interval 0
   vrrp_gna_interval 0
}

vrrp_script chk_nginx {
   script "/usr/local/bin/check_nginx_is_running.sh"
   interval 2 # 脚本执行时间，单位秒
   weight 2
}

vrrp_instance VI_1 {
    state BACKUP
    interface eth1
    virtual_router_id 51
    priority 80
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.110
    }
    track_script {
        chk_nginx
    }
}

# 启动keepalived
systemctl start keepalived
```

### 备节点2 
```shell script
vi /etc/keepalived/keepalived.conf
# 内容如下
! Configuration File for keepalived

global_defs {
   notification_email {
     acassen@firewall.loc
     failover@firewall.loc
     sysadmin@firewall.loc
   }
   notification_email_from Alexandre.Cassen@firewall.loc
   smtp_server 192.168.200.1
   smtp_connect_timeout 30
   router_id 192.168.56.113
   vrrp_skip_check_adv_addr
   # vrrp_strict
   vrrp_garp_interval 0
   vrrp_gna_interval 0
}

vrrp_script chk_nginx {
   script "/usr/local/bin/check_nginx_is_running.sh"
   interval 2 # 脚本执行时间，单位秒
   weight 2
}

vrrp_instance VI_1 {
    state BACKUP
    interface eth1
    virtual_router_id 51
    priority 50
    advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    virtual_ipaddress {
        192.168.56.110
    }
    track_script {
        chk_nginx
    }
}


# 启动keepalived
systemctl start keepalived
```

## 测试
1. 在宿主机浏览器访问`http://192.168.56.110/`，观察到主节点nginx正常运行
2. 在主节点停止nginx `pkill nginx`，`ps -ef | grep nginx`观察到nginx已停止，`ps -ef | grep keepalived`观察到keepalived也停止了
3. 在宿主机浏览器访问`http://192.168.56.110/`，观察到nginx已切到备节点1，因为备节点1的priority=80大于备节点2的50
4. 在备节点1停止nginx `pkill nginx`，此时备节点1的nginx和keepalived停止服务，在宿主机浏览器访问`http://192.168.56.110/`，观察到nginx已切到备节点2

## 例外
直接使用`keepalived`而不是`systemctl start keepalived`来启动keepalived时，`pkill nginx`后，nginx会被重新拉起
https://askubuntu.com/questions/903354/difference-between-systemctl-and-service-commands