package com.example.TelConnect.config;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import java.util.*;
import java.util.concurrent.ExecutionException;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class RedisConnectionTest {
    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379");

        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisAsyncCommands<String, String> asyncCommands = connection.async();

            // Asynchronously store & retrieve a simple string
            asyncCommands.set("foo", "bar").get();
            System.out.println(asyncCommands.get("foo").get());

            // Asynchronously store key-value pairs in a hash directly
            Map<String, String> hash = new HashMap<>();
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            asyncCommands.hset("user-session:123", hash).get();

            System.out.println(asyncCommands.hgetall("user-session:123").get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            redisClient.shutdown();
        }
    }
}
