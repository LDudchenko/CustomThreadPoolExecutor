import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CustomExecutor<TArg> {
    static LinkedBlockingQueue<WorkItem> queue;
    private List<WorkerThread> workerThreads;
    static boolean isShutdown;

    public CustomExecutor(int quantity) {
        queue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>();
        for(int i = 0; i<quantity; i++){
            workerThreads.add(new WorkerThread());
            workerThreads.get(i).start();
        }
    }

    public FutureResult execute(Function func, TArg arg){
        WorkItem task = new WorkItem(func, arg);
        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
        return task.getFuture();
    }

    public List<FutureResult> map(Function func, List<TArg> args){
        return args.stream().map(arg -> this.execute(func, arg)).collect(Collectors.toList());
    }

    public void shutdown() {
        isShutdown = true;
    }
}
