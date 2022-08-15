package com.github.yunfeng.service;

import com.github.yunfeng.entity.Country;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Test
    @DisplayName("测试保存城市")
    @Transactional
    void saveCountry() {
        Country country = countryService.saveCountry(Country.builder().id(6L).name("CHINA").build());
        Assertions.assertEquals(6, country.getId());
    }

    @Test
    @DisplayName("测试查询城市")
    void getCountry() {
        List<Country> countries = countryService.fetchCountryList();
        Assertions.assertEquals(5L, countries.size());
    }
}