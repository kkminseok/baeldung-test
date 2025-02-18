package manipulations.advance;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AnagramTest {

    private static int CHARACTER_RANGE= 256;

    @Test
    public void java_core_anagram() {
        Assertions.assertTrue(isAnagramSort("!low-salt!", "owls-lat!!"));
    }



    public static boolean isAnagramSort(String string1, String string2) {
        if(string1.length() != string2.length()){
            return false;
        }

        char[] a1 = string1.toCharArray();
        char[] a2 = string2.toCharArray();

        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    @Test
    public void use_histogram_anagram_test() {
        Assertions.assertTrue(isAnagramCounting("!low-salt!", "owls-lat!!"));
    }

    public static boolean isAnagramCounting(String string1, String string2) {
        if(string1.length() != string2.length()){
            return false;
        }

        int count[] = new int[CHARACTER_RANGE];
        for(int i = 0; i< string1.length(); ++i) {
            count[string1.charAt(i)]++;
            count[string2.charAt(i)]--;
        }

        for(int i = 0; i< CHARACTER_RANGE; ++i) {
            if(count[i] != 0){
                return false;
            }
        }
        return true;
    }

    @Test
    public void use_multiset_anagram_test() {
        Assertions.assertTrue(isAnagramMultiset("!low-salt!", "owls-lat!!"));
    }

    public static boolean isAnagramMultiset(String string1, String string2) {
        if(string1.length() != string2.length()){
            return false;
        }

        Multiset<Character> multiset1 = HashMultiset.create();
        Multiset<Character> multiset2 = HashMultiset.create();

        for(int i =0; i<string1.length(); ++i) {
            multiset1.add(string1.charAt(i));
            multiset2.add(string2.charAt(i));
        }
        return multiset1.equals(multiset2);
    }
}
