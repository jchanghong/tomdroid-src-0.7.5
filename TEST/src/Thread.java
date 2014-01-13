import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by jchanghong on 14-1-13.
 */
public class Thread {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(runnable);
        pool.execute(runnable);
        pool.execute(runnable);
    }

  static   Runnable runnable=new Runnable() {
      @Override
      public void run() {
          for (int i = 0; i < 3; i++) {
              System.out.println(java.lang.Thread.currentThread().getName()+":" + i);
          }
      }
  }  ;
}
