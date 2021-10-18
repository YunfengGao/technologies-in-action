package com.github.yunfeng.jackson;

import java.util.List;

public class ClassWithoutSetter {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    static class Data {
        private String id;

        public String getId() {
            return id;
        }
    }
}
