/**
 * Created by joel on 10/6/16.
 */
public class Common {
    // yes, using System.err.println() directly would be easy, but I'm not
    // convinced that stderr is the only thing I want to do with the error
    // log, and this makes it easier to change later
    public static void logError(String error) {
        System.err.println(error);
    }
}
