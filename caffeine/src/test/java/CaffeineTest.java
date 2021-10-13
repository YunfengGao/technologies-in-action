import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CaffeineTest {
    @Test
    @DisplayName("测试Caffeine内元素在1秒后过期")
    void testCaffeineExpire() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder().maximumSize(1)
                                              .expireAfterWrite(1, TimeUnit.SECONDS)
                                              .build();
        cache.put("a", "b");
        Assertions.assertEquals("b", cache.getIfPresent("a"));
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertNull(cache.getIfPresent("a"));
    }

    @Test
    @DisplayName("1.测试Caffeine内元素个数最大值")
    void testCaffeineSizeLimit() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder().maximumSize(1).build();
        cache.put("a", "b");
        cache.put("c", "d");
        Assertions.assertEquals(2, cache.estimatedSize());
        Assertions.assertEquals("b", cache.getIfPresent("a"));
        Assertions.assertEquals("d", cache.getIfPresent("c"));
        TimeUnit.SECONDS.sleep(2);
        Assertions.assertEquals(1, cache.estimatedSize());
    }

    @Test
    @DisplayName("2.测试Caffeine内元素个数最大值")
    void testCaffeineSizeLimit2() {
        Cache<String, String> cache = Caffeine.newBuilder().maximumSize(1).build();
        cache.put("a", "b");
        cache.put("c", "d");
        Assertions.assertEquals(2, cache.estimatedSize());
        Assertions.assertEquals("d", cache.getIfPresent("c"));
        Assertions.assertNull(cache.getIfPresent("a"));
        Assertions.assertEquals(1, cache.estimatedSize());
    }

    @Test
    @DisplayName("2秒未写入/更新/读取，缓存过期并失效")
    void testExpireAfterAccess() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                                              .maximumSize(1)
                                              .expireAfterAccess(2, TimeUnit.SECONDS)
                                              .build();
        cache.put("a", "b");
        TimeUnit.SECONDS.sleep(1);
        cache.getIfPresent("a");
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals("b", cache.getIfPresent("a"));
        TimeUnit.SECONDS.sleep(1);
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertNull(cache.getIfPresent("a"));
    }
}
