package com.github.yunfeng.jackson;

public class ClassWithoutSetter {
    public ClassWithSetter.Inner getInner() {
        return inner;
    }

    private ClassWithSetter.Inner inner;

    static class Inner {
        public String getId() {
            return id;
        }

        private String id;
    }
}
