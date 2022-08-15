package com.github.yunfeng.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {
    @Id
    private Long id;
    private String name;
}
