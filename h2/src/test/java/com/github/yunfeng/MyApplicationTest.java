package com.github.yunfeng;

import com.github.yunfeng.util.StringUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

class MyApplicationTest {
    @Test
    @DisplayName("测试只mock工具类中的一个静态方法")
    void main() {
        try (MockedStatic<StringUtils> utilities = Mockito.mockStatic(StringUtils.class)) {
            utilities.when(() -> StringUtils.getLength(any())).thenReturn(1);
            int length = StringUtils.getLength("");
            Assertions.assertEquals(1, length);
            int length2 = StringUtils.getLength2();
            Assertions.assertEquals(0, length2);
        }
    }
}