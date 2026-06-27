package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionTests {
    @Test
    void arrayTests(){
        //var array = new String[]{"a","b","c"};
        var array = new String[3];
        Assertions.assertEquals(3, array.length);
        array[0] = "a";
        Assertions.assertEquals("a",array[0]);
        array[0]= "d";
        Assertions.assertEquals("d",array[0]);

    }

    @Test
    void listTest(){
        var list = new ArrayList<>(List.of("a","b","c", "a")); //Создание списка с предустановленными значениями. МОДИФИЦИРУЕМЫЙ
        Assertions.assertEquals(4,list.size());
        Assertions.assertEquals("a", list.get(0));
        list.set(0, "d");//изминение эллимента списка
    }

    @Test
    void setTest(){
        var set = new HashSet<>(List.of("a", "b", "c", "a")); //При переводе лист в множество, дубликарты затираются
        Assertions.assertEquals(3,set.size()); //Проверяем что размер == 3
        //var element = set.stream().findAny().get(); //Вернётся случвайный элимент из множества

        set.add("d");
        Assertions.assertEquals(4,set.size()); //Благодаря new HashSet<>(можно реализовыват изменяемое в размере множество)
    }

    @Test
    void testMap() {
        var digits = new HashMap<Character, String>();
        digits.put('1', "one");
        digits.put('2', "two");
        digits.put('3', "three");
        Assertions.assertEquals("one", digits.get('1'));
        digits.put('1', "один");
        Assertions.assertEquals("one", digits.get('1'));
    }
}
