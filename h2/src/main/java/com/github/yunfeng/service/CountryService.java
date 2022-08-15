package com.github.yunfeng.service;

import com.github.yunfeng.entity.Country;

import java.util.List;

public interface CountryService {
    Country saveCountry(Country country);

    List<Country> fetchCountryList();
}
