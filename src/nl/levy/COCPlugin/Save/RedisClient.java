package nl.levy.COCPlugin.Save;

import com.google.gson.Gson;
import nl.levy.COCPlugin.COCManager.COCManager;
import org.bukkit.entity.HumanEntity;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class RedisClient {


    public RedisClient() {

    }

    public String get(String s) {
        System.out.println("Get " + s);
        Jedis jedis = new Jedis("redis://default:8ghzVFGZFd7FguA3jti48jcEKQXsqmqC@redis-12222.c135.eu-central-1-1.ec2.cloud.redislabs.com:12222");

        var res =  jedis.get(s);
        jedis.close();

        return res;
    }

    public void set(String s, String s2) {
        System.out.println("set " + s);
        Jedis jedis = new Jedis("redis://default:8ghzVFGZFd7FguA3jti48jcEKQXsqmqC@redis-12222.c135.eu-central-1-1.ec2.cloud.redislabs.com:12222");
        jedis.set(s, s2);
        jedis.close();
    }

    public COCManager getManager(UUID player) {
        var text = get("base/" + player);
        if (text != null) {
            return new COCManager(new Gson().fromJson(text, SaveCOCManager.class), player);
        }

        return new COCManager(player);
    }


    public void save(UUID player, COCManager manager) {
        var text = new Gson().toJson(SaveCOCManager.from(manager));
        set("base/" + player, text);
    }
}
