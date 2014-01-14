import java.io.File;
import java.util.List;

/**
 * Created by jchanghong on 14-1-13.
 */
public class Thread {
    public static List<String> STRING = Str.ff;

    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        pool.execute(runnable);
//        pool.execute(runnable);
//        pool.execute(runnable);
        String str = "ddd_111.note";
        File file = new File(str);

        System.out.println(file.lastModified());
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());



    }

  static   Runnable runnable=new Runnable() {
      @Override
      public void run() {
          for (int i = 0; i < 3; i++) {
              System.out.println(java.lang.Thread.currentThread().getName()+":" + i);
          }
      }
  }  ;
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
}
