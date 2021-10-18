package com.github.yunfeng.jackson;

import java.util.List;

public class ClassWithSetter {
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(final List<Data> data) {
        this.data = data;
    }

    static class Data {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(final String id) {
            this.id = id;
        }
    }
}
