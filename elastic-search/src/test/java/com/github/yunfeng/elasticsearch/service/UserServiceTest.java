package com.github.yunfeng.elasticsearch.service;

import com.github.yunfeng.elasticsearch.entity.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void save() {
        User user = User.builder().id(1L).name("haha").build();
        User save = userService.save(user);
        Assertions.assertEquals(user.getId(), save.getId());
    }

    @Test
    void count() {
        long count = userService.count();
        Assertions.assertEquals(1, count);
    }

    @Test
    void delete() {
        User user = User.builder().id(2L).name("haha").build();
        userService.save(user);
        userService.delete(user);
    }

    @Test
    void getAll() {
        Iterable<User> users = userService.getAll();
        users.forEach(user -> {
            Long id = user.getId();
            Assertions.assertEquals(1, id);
        });
    }
}