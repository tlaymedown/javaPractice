package life.tz.JavaGuide.ThreadPool;

public class WorkThread implements Runnable{

    private String command;

    public WorkThread(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println("begin to command thread : " + Thread.currentThread().getName() + " : " + command);
        processCommand();
        System.out.println("end to command thread : " + Thread.currentThread().getName());

    }

    private void processCommand() {
        try {
            Thread.sleep(2 * 2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString(){
        return this.command;
    }


}
