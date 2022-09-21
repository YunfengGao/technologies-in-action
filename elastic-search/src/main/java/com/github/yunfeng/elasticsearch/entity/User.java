package com.github.yunfeng.elasticsearch.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "user")
@Getter
@Setter
@Builder
public class User {
    @Id
    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String city;
}
