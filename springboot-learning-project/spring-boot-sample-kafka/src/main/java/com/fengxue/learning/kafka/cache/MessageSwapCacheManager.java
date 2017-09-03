package com.fengxue.learning.kafka.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MessageSwapCacheManager {

    private static ConcurrentHashMap<String, String> hashMapSwapCache = new ConcurrentHashMap<String, String>();
    private volatile boolean isCopying = false;

    public HashMap<String, String> copyAndClearSwapCache() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        isCopying = true;
        hashMap.putAll(hashMapSwapCache);
        hashMap.clear();
        isCopying = false;
        return hashMap;
    }

    public int size() {
        return hashMapSwapCache.size();
    }

    public boolean isEmpty() {
        return hashMapSwapCache.isEmpty();
    }

    public String get(String key) {
        return hashMapSwapCache.get(key);
    }

    public String put(String key, String value) {
        return hashMapSwapCache.put(key, value);
    }

}
