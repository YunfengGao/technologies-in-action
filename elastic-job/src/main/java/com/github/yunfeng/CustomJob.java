package com.github.yunfeng;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class CustomJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.printf("------Thread ID: %s, 任务总片数: %s, 当前分片项: %s%n",
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem());
    }
}
