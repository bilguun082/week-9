package debugging;

public class WordAnalyzerTester
{
    public static void main(String[] args)
    {
        System.out.println("=== Testing firstRepeatedCharacter() ===");
        testRepeated("aardvark"); // expect: a
        testRepeated("roommate"); // expect: o (not m)
        testRepeated("mate");     // expect: 0 (no duplicate letters)
        testRepeated("test");     // expect: 0 (the t isn't repeating adjacent)

        System.out.println("\n=== Testing firstMultipleCharacter() ===");
        testMultiple("missisippi"); // expect: i (or s depending on search order, 'm' is unique, 'i' repeats at pos 4)
        testMultiple("mate");       // expect: 0 (no multiple characters)
        testMultiple("test");       // expect: t (t occurs at 0 and 3)

        System.out.println("\n=== Testing countRepeatedCharacters() ===");
        testCount("mississippi!!!"); // expect: 4 (ss, ss, pp, !!!)
        testCount("aabb");           // expect: 2 (aa, bb)
        testCount("aardvark");       // expect: 1 (aa)
        testCount("mate");           // expect: 0
    }

    public static void testRepeated(String s)
    {
        WordAnalyzer wa = new WordAnalyzer(s);
        char result = wa.firstRepeatedCharacter();
        if (result == 0)
            System.out.println("Word: \"" + s + "\" -> No repeated character.");
        else
            System.out.println("Word: \"" + s + "\" -> First repeated character = " + result);
    }

    public static void testMultiple(String s)
    {
        WordAnalyzer wa = new WordAnalyzer(s);
        char result = wa.firstMultipleCharacter();
        if (result == 0)
            System.out.println("Word: \"" + s + "\" -> No multiple character.");
        else
            System.out.println("Word: \"" + s + "\" -> First multiple character = " + result);
    }

    public static void testCount(String s)
    {
        WordAnalyzer wa = new WordAnalyzer(s);
        int result = wa.countRepeatedCharacters();
        System.out.println("Word: \"" + s + "\" -> Repeated groups count = " + result);
    }
}
