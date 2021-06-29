import io.swagger.client.ApiException;
import io.swagger.client.api.TextbodyApi;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

public class getThread implements Runnable {

  private final TextbodyApi textbodyApi;
  private long restime;
  private int count;
  private long maxres;
  private ArrayList<Long> reslist;

  public getThread(TextbodyApi textbodyApi) {
    this.textbodyApi = textbodyApi;
    this.reslist = new ArrayList<>();
    this.restime = Long.parseLong("0");
    this.count = 0;
    this.maxres = Long.parseLong("0");
  }

  @Override
  public void run() {
    String[] lst = {"to", "as", "more", "is", "used", "this", "in", "of", "we", "how"};
    while (true) {
      try {
        Thread.sleep(1000);

        for (int i=0; i<10; i++) {
          if (ClientConstant.GET_THREAD) {
            long startTime = System.currentTimeMillis();
            BigDecimal res = this.textbodyApi.getWordCount(lst[i]);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            this.reslist.add(totalTime);
            this.count += 1;
          } else {
            break;
          }

        }
        if (!ClientConstant.GET_THREAD) {
          for (Long i : this.reslist) {
            this.restime += i;
            this.maxres = Math.max(this.maxres, i);
          }
          if (this.count != 0) {
            System.out.println("Mean GET response time:" + this.restime / this.count);
            System.out.println("Max GET response time:" + this.maxres);
          } else {
            System.out.println("No get request");
          }
          break;
        }

      } catch (InterruptedException | ApiException e) {
        e.printStackTrace();
      }
    }
  }
}
