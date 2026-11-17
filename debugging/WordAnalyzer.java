package debugging;

/**
 * A class that analyzes words (corrected version).
 * Based on Cay Horstmann's debugging tutorial.
 */
public class WordAnalyzer
{
    /**
     * Constructs an analyzer for a given word.
     * @param aWord the word to be analyzed
     */
    public WordAnalyzer(String aWord)
    {
        word = aWord;
    }

    /**
     * Gets the first repeated character. A character is <i>repeated</i>
     * if it occurs at least twice in adjacent positions. For example,
     * 'l' is repeated in "hollow", but 'o' is not.
     * @return the first repeated character, or 0 if none found
     */
    public char firstRepeatedCharacter()
    {
        // FIX 1: Change upper bound to word.length() - 1 to avoid StringIndexOutOfBoundsException
        for (int i = 0; i < word.length() - 1; i++)
        {
            char ch = word.charAt(i);
            if (ch == word.charAt(i + 1))
                return ch;
        }
        return 0;
    }

    /**
     * Gets the first multiply occuring character. A character is <i>multiple</i>
     * if it occurs at least twice in the word, not necessarily in adjacent positions.
     * For example, both 'o' and 'l' are multiple in "hollow", but 'h' is not.
     * @return the first repeated character, or 0 if none found
     */
    public char firstMultipleCharacter()
    {
        for (int i = 0; i < word.length(); i++)
        {
            char ch = word.charAt(i);
            // FIX 2: Search from i + 1, otherwise find(ch, i) matches itself at index i
            if (find(ch, i + 1) >= 0)
                return ch;
        }
        return 0;
    }

    private int find(char c, int pos)
    {
        for (int i = pos; i < word.length(); i++)
        {
            if (word.charAt(i) == c)
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Counts the groups of repeated characters. For example, "mississippi!!!" has
     * four such groups: ss, ss, pp and !!!.
     * @return the number of repeated character groups
     */
    public int countRepeatedCharacters()
    {
        int c = 0;
        // FIX 3: Start from i = 0 to capture repeated characters at the beginning of the word.
        // Check (i == 0 || word.charAt(i - 1) != word.charAt(i)) to guard the start of a group.
        for (int i = 0; i < word.length() - 1; i++)
        {
            if (word.charAt(i) == word.charAt(i + 1)) // found a repetition
            {
                if (i == 0 || word.charAt(i - 1) != word.charAt(i)) // it's the start
                    c++;
            }
        }
        return c;
    }

    private String word;
}
