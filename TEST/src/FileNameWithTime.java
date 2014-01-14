import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by changhong on 14-1-14.
 */
public class FileNameWithTime {
    public static void main(String[] args) {
        System.out.println(getPushable());
    }

    public static List<Map<String, String>> getReplacelocal() {
        return replacelocal;
    }

    public static List<Map<String, String>> getReplaceremot() {
        return replaceremot;
    }

    public static List<String> getPushable() {
        pushable.clear();
        for (String a :  locals) {
            System.out.println(locals);
            String uul = nameToUUL(a);
            for (String e : remotl) {
                if (uul.equals(nameToUUL(e))) {
                    break;

                }
            }

        }
        return pushable;
    }

    public static List<String> getPullable() {
        return pullable;

    }

    private static String nameToUUL(String name) {
        String res;
        int index = name.lastIndexOf("_");
        res = name.substring(0, index);
        res = res + name.substring(name.lastIndexOf("."), name.length());
        return res;
    }

    private static String uulToName(String uul) {
        String res;
        int index = uul.lastIndexOf(".");
        res = uul.substring(0, index);
        uul = res + "_" + String.valueOf(System.currentTimeMillis()) + ".note";
        return uul;
    }
    public static final String LOCAL = "local";
    public static final String REMOT = "remot";
    private static List<Map<String, String>> replacelocal = new ArrayList<Map<String, String>>();
    private static List<Map<String, String>> replaceremot = new ArrayList<Map<String, String>>();
    private static List<String> pushable = new ArrayList<String>();
    private static List<String> pullable = new ArrayList<String>();
    private static List<String> locals = new ArrayList<String>();
    private static List<String> remotl = new ArrayList<String>();

    static {

        locals.add(uulToName("aaaa.note"));
        locals.add(uulToName("dddd.note"));
        remotl.add(uulToName("dddd.note"));
        remotl.add(uulToName("dddssds.note"));
    }


}
