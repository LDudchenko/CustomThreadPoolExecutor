public class WorkerThread extends Thread{

    @Override
    public void run(){
        while(true){
            if(CustomExecutor.queue.isEmpty() && CustomExecutor.isShutdown){
                return;
            }

            WorkItem item = null;
            try {
                item = CustomExecutor.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Object result = item.getFunc().apply(item.getArg());
            item.getFuture().setResult(result);
        }
    }
}
