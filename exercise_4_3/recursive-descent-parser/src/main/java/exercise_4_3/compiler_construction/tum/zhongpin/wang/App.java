package exercise_4_3.compiler_construction.tum.zhongpin.wang;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // test the implementation
        Parser p = new Parser();
        p.parse("s");
        p.parse("a");
        p.parse("b");
        p.parse("sa");
        p.parse("aaaas");
    }
}
