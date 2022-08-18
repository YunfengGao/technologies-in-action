package com.github.yunfeng;

import com.github.yunfeng.util.EnvUtils;
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
    void testMockStatic() {
        try (MockedStatic<StringUtils> utilities = Mockito.mockStatic(StringUtils.class)) {
            utilities.when(() -> StringUtils.getLength(any())).thenReturn(1);
            int length = StringUtils.getLength("");
            Assertions.assertEquals(1, length);
            int length2 = StringUtils.getLength2();
            Assertions.assertEquals(0, length2);
        }
    }

    @Test
    @DisplayName("测试先mock工具类内部方法，再mock工具类，结果mock功能正常")
    void testMockStatic2() {
        try (MockedStatic<EnvUtils> utilities = Mockito.mockStatic(EnvUtils.class)) {
            utilities.when(() -> EnvUtils.getEnv(any())).thenReturn("xxxx");
            int length2 = StringUtils.getLength2();
            Assertions.assertEquals("xxxx".length(), length2);
        }
        try (MockedStatic<StringUtils> utilities = Mockito.mockStatic(StringUtils.class)) {
            utilities.when(() -> StringUtils.getLength(any())).thenReturn(1);
            int length = StringUtils.getLength("");
            Assertions.assertEquals(1, length);
        }
    }

    @Test
    // 这个现象只有单独运行才存在
    @DisplayName("测试3互换了测试2中两个方法的顺序，会导致内部的static-mock不生效而抛出NPE")
    void testMockStatic3() {
        try (MockedStatic<StringUtils> utilities = Mockito.mockStatic(StringUtils.class)) {
            utilities.when(() -> StringUtils.getLength(any())).thenReturn(1);
            int length = StringUtils.getLength("");
            Assertions.assertEquals(1, length);
        }
        try (MockedStatic<EnvUtils> utilities = Mockito.mockStatic(EnvUtils.class)) {
            utilities.when(() -> EnvUtils.getEnv(any())).thenReturn("xxxx");
            Assertions.assertThrowsExactly(NullPointerException.class, () -> StringUtils.getLength2());
        }
    }
}