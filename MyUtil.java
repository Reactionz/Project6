import java.util.Random;

public class MyUtil {
    static int pcGap = 5;
    static Random rnd = new Random() ; 
    public static void  bell() { System.out.printf("%c", (char)(7)); }
    public static void  clear() { System.out.printf("%s", (char)(27) +"[2J") ; }
    public static void  moveTo(int x, int y ) { System.out.printf("%s", (char)(27) + "[" + x  +";" + y +"H"); }
    public static void eraseLine() { System.out.printf ("%s", (char)(27) + "[K"); }
    static void simulate(int x, int y,  String msg, int ms ) {
        MyUtil.moveTo(x, y); 
        MyUtil.eraseLine();
        System.out.printf("%s", msg);
        try { Thread.sleep ( MyUtil.rnd.nextInt( ms ) + 500); } catch (Exception e) {}
    } 
}
