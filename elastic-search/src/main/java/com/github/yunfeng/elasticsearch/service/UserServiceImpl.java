package com.github.yunfeng.elasticsearch.service;

import com.github.yunfeng.elasticsearch.entity.User;
import com.github.yunfeng.elasticsearch.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

//    @Override
//    public List<User> getByName(String name) {
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);
//        Iterable<User> iterable = userRepository.
//    }
//
//    public Page<User> pageQuery(Integer pageNo, Integer pageSize, String kw) {
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchPhraseQuery("name", kw)).withPageable(PageRequest.of(pageNo, pageSize)).build();
//        return userRepository.s
//    }
}
