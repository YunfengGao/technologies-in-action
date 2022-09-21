package com.github.yunfeng.elasticsearch.repository;

import com.github.yunfeng.elasticsearch.entity.User;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
}
