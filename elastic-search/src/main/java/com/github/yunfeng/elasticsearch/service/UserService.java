package com.github.yunfeng.elasticsearch.service;

import com.github.yunfeng.elasticsearch.entity.User;

public interface UserService {
    User save(User user);

    long count();

    void delete(User user);

    Iterable<User> getAll();

//    List<User> getByName(String name);
}
