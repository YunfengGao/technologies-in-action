package com.github.yunfeng.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user")
public class User {
    @Id
    private String id;
    private String name;
    private String sex;
    private Integer age;
    private String city;
}
