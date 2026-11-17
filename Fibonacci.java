/**
 * Iterative Fibonacci sequence calculator.
 */
public class Fibonacci
{
    /**
     * Default constructor for the Fibonacci utility class.
     */
    public Fibonacci()
    {
    }

    /**
     * Computes the n-th Fibonacci number iteratively according to the recurrence:
     * f(0) = 1, f(1) = 1, and f(n) = f(n - 1) + f(n - 2) for n &gt; 1.
     *
     * @param n The non-negative index in the Fibonacci sequence
     * @return The n-th Fibonacci number
     */
    public static int fib(int n)
    {
        // Initializing f to 1 ensures f(0) = 1 and f(1) = 1 when while loop does not run.
        int f = 1;
        int f0 = 1;
        int f1 = 1;
        while (n > 1)
        {
            n--;
            f = f0 + f1;
            f0 = f1;
            f1 = f;
        }
        return f;
    }

    /**
     * Main execution entry point demonstrating sample Fibonacci outputs.
     *
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args)
    {
        System.out.println("Fibonacci Test Cases (expected f(0)=1, f(1)=1, f(n)=f(n-1)+f(n-2)):");
        for (int i = 0; i <= 8; i++)
        {
            System.out.println("f(" + i + ") = " + fib(i));
        }
    }
}
