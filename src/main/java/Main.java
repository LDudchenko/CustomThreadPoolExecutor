import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        MyFunction func = new MyFunction();
        CustomExecutor customExecutor = new CustomExecutor(2);
        List<FutureResult> futures = customExecutor.map(func, List.of(1, 2, 3, 4));
        customExecutor.shutdown();

        for(FutureResult f : futures){
            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            System.out.println(timeStamp + " - "+f.getResult());
        }
    }
}

class MyFunction implements Function<Integer, Integer>{

    @Override
    public Integer apply(Integer x) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return x*2;
    }
}
