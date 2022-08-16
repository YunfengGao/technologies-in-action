package com.github.yunfeng.service;

import com.github.yunfeng.entity.Country;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Test
    @DisplayName("测试保存城市后，清理context，防止插入的数据影响其它UT")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void saveCountry() {
        Country country = countryService.saveCountry(Country.builder().id(6L).name("CHINA").build());
        Assertions.assertEquals(6, country.getId());
    }

    @Test
    @DisplayName("测试保存城市后，使用事务回滚，防止插入的数据影响其它UT")
    @Transactional
    void saveCountry2() {
        Country country = countryService.saveCountry(Country.builder().id(7L).name("RUSSIA").build());
        Assertions.assertEquals(7, country.getId());
    }

    @Test
    @DisplayName("测试查询城市")
    void getCountry() {
        List<Country> countries = countryService.fetchCountryList();
        Assertions.assertEquals(5L, countries.size());
    }
}