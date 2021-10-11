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
}
