import org.example.MyExamples;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestApp {
    private MyExamples myExamples = new MyExamples();

    @Test
    public void myTest(){
        boolean result = myExamples.checkPrime(10);
        assertFalse(result);
    }

    @Test
    public void checkIfZeroAndOneWillNotPrime(){
        assertFalse(myExamples.checkPrime(1));
        assertFalse(myExamples.checkPrime(0));
    }

    @Test
    public  void  checkNumberIsPrime(){
        assertTrue(myExamples.checkPrime(19));
        assertTrue(myExamples.checkPrime(37));
        assertFalse(myExamples.checkPrime(28));
    }

    @Test
    public void printFiboTest(){
        myExamples.printFibo(1,40,1);
    }

    @Test
    public  void checkPalindrom(){
        assertFalse(myExamples.checkPalindromString("alper"));
        assertTrue(myExamples.checkPalindromString("abba"));
    }

    @Test
    public void factorialTest(){
        assertEquals(6,myExamples.findFactorial(3));
        assertEquals(120,myExamples.findFactorial(5));
        assertNotEquals(600,myExamples.findFactorial(10));
        assertEquals(24,myExamples.findFactorial(4));
    }

    @Test
    public  void  containsElementsTest(){
        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = new ArrayList<>();
        List<Integer> listC = new ArrayList<>();

        listA.add(1);
        listA.add(2);
        listA.add(5);
        listA.add(6);

        listB.add(8);
        listB.add(9);
        listB.add(19);

        listC.add(1);
        listC.add(2);
        listC.add(5);

        assertFalse(myExamples.containsElements(listA,listB));
        assertTrue(myExamples.containsElements(listA,listC));
    }

    @Test
    public void findSecondElementInList(){
        List<Integer> listA = new ArrayList<>();
        listA.add(9);
        listA.add(8);
        listA.add(5);
        listA.add(6);
        listA.add(1);

        assertEquals(8,myExamples.findSecondLargestElement(listA));
        assertNotEquals(5,myExamples.findSecondLargestElement(listA));
    }

    @Test
    public void findDistinctCharsAndCountTest(){
        String str = "alpermulayim";

        Map<Character,Integer> map = myExamples.findDistinctCharsAndCount(str);
//        System.out.println(map);
        assertEquals(Optional.of(2).get(),map.get('m'));
        assertEquals(Optional.of(2).get(),map.get('a'));
    }

    @DisplayName("test a display")
    @Test
    public void testfindTheDublicateElements(){
        List<String> names = new LinkedList<>();
        names.add("alper");
        names.add("alp");
        names.add("alper");
        names.add("mulayim");
        names.add("alper");
        names.add("mulayim");
        names.add("mugla");

        Set<String> dublicates = myExamples.findTheDublicateElements(names);
        
        assertEquals(2,dublicates.size());
        assertTrue(dublicates.contains("alper"));
        assertTrue(dublicates.contains("mulayim"));
        assertFalse(dublicates.contains("mugla"));
    }
}
