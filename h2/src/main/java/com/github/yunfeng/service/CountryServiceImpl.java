package com.github.yunfeng.service;

import com.github.yunfeng.entity.Country;
import com.github.yunfeng.repository.CountryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public List<Country> fetchCountryList() {
        return (List<Country>) countryRepository.findAll();
    }
}
