import redis.clients.jedis.Jedis;

public class JedisSingle {

    public static void main(String[] args) {
        try (Jedis jedis = new Jedis("192.168.56.120", 6379)) {
            jedis.auth("foobared");
            String value = jedis.get("key");
            System.out.println(value);
        }
    }
}
