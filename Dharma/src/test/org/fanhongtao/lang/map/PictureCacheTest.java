package org.fanhongtao.lang.map;

import org.fanhongtao.lang.map.Picture;
import org.fanhongtao.lang.map.PictureLRUCache;

/**
 * @author Dharma
 * @created 2010-3-5
 */
public class PictureCacheTest
{

    private static void test_1()
    {
        PictureLRUCache cache = new PictureLRUCache();
        System.out.println("Upper limit: " + cache.getUpperLimit() + " ,  lower limit" + cache.getLowerLimit());
        cache.put("one", new Picture(100, "one"));
        cache.put("two", new Picture(200, "two"));
        cache.put("three", new Picture(300, "three"));
        cache.show("before get one");
        cache.get("one");
        cache.show("after get one");
        cache.put("four", new Picture(400, "four"));
        cache.show("after add four");
        cache.put("five", new Picture(500, "five"));
        cache.show("after add five");
        cache.put("six", new Picture(600, "six"));
        cache.show("after add six");
        cache.forceDelete();
        cache.show("after force delete");
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        test_1();
    }

}
