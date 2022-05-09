import java.util.function.Function;

public class WorkItem<TArg, TResult> {
    private Function<FutureResult, TResult> func;
    private TArg arg;
    private FutureResult<TResult> future;

    public WorkItem(Function<FutureResult, TResult> func, TArg arg) {
        this.func = func;
        this.arg = arg;
        future = new FutureResult<>();
    }

    public Function<FutureResult, TResult> getFunc() {
        return func;
    }

    public TArg getArg() {
        return arg;
    }

    public FutureResult<TResult> getFuture() {
        return future;
    }
}

