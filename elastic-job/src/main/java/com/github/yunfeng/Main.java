package com.github.yunfeng;

import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

public class Main {
    private static final ZookeeperConfiguration zkConfig = new ZookeeperConfiguration("localhost:2181", "elastic-job-example");

    private static final CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zkConfig);

    public static void main(String[] args) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder("jobName", "0/3 * * * * ?", 2).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, CustomJob.class.getCanonicalName());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build()).init();
    }
}
