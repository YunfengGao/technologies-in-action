import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

public class RedissonTest {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        for (int i = 0; i < 10; i++) {
            new Thread(counter::add).start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(counter.get());
    }

    static class Counter {
        private int count;
        private final RLock lock;

        public Counter() {
            Config config = new Config();
            config.useSingleServer().setAddress("redis://192.168.56.120:6379").setPassword("foobared");
            RedissonClient client = Redisson.create(config);
            lock = client.getFairLock("lock");
        }

        public int add() {
            try {
                lock.lock();
                this.count++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return count;
        }

        public int get() {
            return this.count;
        }
    }
}
