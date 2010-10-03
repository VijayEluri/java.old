package org.fanhongtao.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dharma
 * @created 2010-3-7
 */
public class DumpTest
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        dumpObject();
        dumpMap();
        dumpList();
        
        dumpMapList();
        dumpListMap();
        
        dumpArray();
        dumpArrayMap();
        dumpArrayList();
        
        dumpDup();
    }
    
    private static void dumpObject()
    {
        System.out.println(new Dump().toString("Null Object", null));
        System.out.println(new Dump().toString("\nDump Object", new Person("jack", 11)));
    }
    
    private static void dumpMap()
    {
        Map<String, Person> map = getMap();
        System.out.println(new Dump().toString("\nDump Map", map));
    }
    
    private static void add(Map<String, Person> map, Person person)
    {
        map.put(person.getName(), person);
    }
    
    private static void dumpList()
    {
        List<Person> list = getList();
        System.out.println("\nDump List");
        System.out.println(new Dump().toString(list));
    }
    
    private static void dumpMapList()
    {
        List<Map<String, Person>> list = new ArrayList<Map<String, Person>>();
        list.add(getMap());
        list.add(getMap());
        System.out.println("\nDump Map List");
        System.out.println(new Dump().toString(list));
    }
    
    private static void dumpListMap()
    {
        Map<Integer, List<Person>> map = new HashMap<Integer, List<Person>>();
        map.put(1, getList());
        map.put(2, getList());
        System.out.println("\nDump List Map");
        System.out.println(new Dump().toString(map));
    }
    
    private static void dumpArray()
    {
        Object[] objs = getArray();
        System.out.println("\nDump Array");
        System.out.println(new Dump().toString(objs));
    }
    
    private static void dumpArrayMap()
    {
        Map<Integer, Object[]> map = new HashMap<Integer, Object[]>();
        map.put(1, getArray());
        map.put(2, getArray());
        System.out.println("\nDump Array Map");
        System.out.println(new Dump().toString(map));
    }
    
    private static void dumpArrayList()
    {
        Object[] objs = getArray();
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(objs);
        System.out.println("\nDump Array List");
        System.out.println(new Dump().toString(list));
    }
    
    private static Map<String, Person> getMap()
    {
        Map<String, Person> map = new HashMap<String, Person>();
        add(map, new Person("jack", 11));
        add(map, new Person("tom", 12));
        add(map, new Person("rose", 13));
        return map;
    }
    
    private static List<Person> getList()
    {
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("jack", 11));
        list.add(new Person("tom", 12));
        list.add(new Person("rose", 13));
        return list;
    }
    
    private static Object[] getArray()
    {
        Object[] objs = new Person[3];
        objs[0] = new Person("jack", 11);
        objs[1] = new Person("tom", 12);
        objs[2] = new Person("rose", 13);
        return objs;
    }
    
    private static void dumpDup()
    {
        A a = new A();
        a.setId("class_a");
        B b = new B();
        b.setId("class_b");
        a.setB(b);
        b.setA(a);
        System.out.println(new Dump().toString("\nReference A:", a));
    }
}

class Person
{
    private String name;
    
    private int age;
    
    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getAge()
    {
        return age;
    }
    
    public void setAge(int age)
    {
        this.age = age;
    }
}

class A
{
    private String id;
    
    private B b;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public B getB()
    {
        return b;
    }
    
    public void setB(B b)
    {
        this.b = b;
    }
    
}

class B
{
    
    private String id;
    
    private A a;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public A getA()
    {
        return a;
    }
    
    public void setA(A a)
    {
        this.a = a;
    }
    
}
