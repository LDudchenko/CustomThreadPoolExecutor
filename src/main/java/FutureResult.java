import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FutureResult<TResult> {
    private boolean hasResult;
    private TResult result;
    private Lock lock;
    private Condition readyCondition;

    public FutureResult() {
        this.lock = new ReentrantLock();
        this.readyCondition = lock.newCondition();
        this.hasResult = false;
    }

    public void setResult(TResult result){
        this.lock.lock();
        this.result = result;
        this.hasResult = true;
        this.readyCondition.signalAll();
        this.lock.unlock();
    }

    public TResult getResult() throws InterruptedException {
        this.lock.lock();

        while(hasResult == false){
            readyCondition.await();
        }

        this.lock.unlock();
        return result;
    }
}
