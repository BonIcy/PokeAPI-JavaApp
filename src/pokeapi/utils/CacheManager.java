/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.utils;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Jesùs
 */


public class CacheManager {
    private static final Map<String, String> cache = new HashMap<>();

    public static void save(String key, String data) {
        cache.put(key.toLowerCase(), data);
    }

    public static String get(String key) {
        return cache.getOrDefault(key.toLowerCase(), "");
    }

    public static boolean contains(String key) {
        return cache.containsKey(key.toLowerCase());
    }

    public static void clear() {
        cache.clear();
    }
}