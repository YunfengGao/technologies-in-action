package com.github.yunfeng.jackson;

public class ClassWithSetter {
    public void setInner(final Inner inner) {
        this.inner = inner;
    }

    public Inner getInner() {
        return inner;
    }

    private Inner inner;

    static class Inner {
        public void setId(final String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        private String id;
    }
}
