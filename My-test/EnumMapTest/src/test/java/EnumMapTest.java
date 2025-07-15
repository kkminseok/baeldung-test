import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumMapTest {

    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    /*
     * EnumMap 기본 생성
     */
    @Test
    void enumGenerationTest() {
        EnumMap<DayOfWeek, Integer> enumMap = new EnumMap<>(DayOfWeek.class);
    }

    /*
     * EnumMap Copy 방법
     */
    @Test
    void enumGenerationCopyTest() {
        EnumMap<DayOfWeek, Integer> enumMap = new EnumMap<>(DayOfWeek.class);
        enumMap.put(DayOfWeek.MONDAY, 1);
        enumMap.put(DayOfWeek.TUESDAY, 2);
        enumMap.put(DayOfWeek.WEDNESDAY, 3);

        EnumMap<DayOfWeek, Integer> copiedEnumMap = new EnumMap<>(enumMap);

        copiedEnumMap.forEach((day, value) -> System.out.println(day + ": " + value));
    }

    @Test
    void enumMapCrudTest() {
        EnumMap<DayOfWeek, String> enumMap = new EnumMap<>(DayOfWeek.class);

        //Create
        enumMap.put(DayOfWeek.MONDAY, "월요일 업무");
        assertEquals(enumMap.size(), 1);

        //Read
        String mondayTask = enumMap.get(DayOfWeek.MONDAY);
        assertEquals(mondayTask, "월요일 업무");

        //Delete
        enumMap.remove(DayOfWeek.MONDAY);
        assertEquals(enumMap.size(), 0);

        //containsKey
        enumMap.put(DayOfWeek.SATURDAY,"쉬기");
        Assertions.assertTrue(enumMap.containsKey(DayOfWeek.SATURDAY));
        Assertions.assertTrue(enumMap.containsKey(DayOfWeek.SUNDAY));

        //clear
        enumMap.clear();
        assertEquals(enumMap.size(), 0);
    }

    @Test
    void enumMapOrderedTest() {
        EnumMap<DayOfWeek, String> schedule = new EnumMap<>(DayOfWeek.class);
        schedule.put(DayOfWeek.MONDAY, "회의");
        schedule.put(DayOfWeek.WEDNESDAY, "발표");
        schedule.put(DayOfWeek.THURSDAY, "보고서 제출");
        schedule.put(DayOfWeek.TUESDAY, "코딩");

        DayOfWeek[] expectedOrder = DayOfWeek.values();


        Iterator<DayOfWeek> keyIterator = schedule.keySet().iterator();
        int i = 0;
        while (keyIterator.hasNext()) {
            DayOfWeek currentDay = keyIterator.next();
            System.out.println(currentDay + " " + expectedOrder[i]);
            assertEquals(expectedOrder[i], currentDay);
            i++;
        }
    }


}
