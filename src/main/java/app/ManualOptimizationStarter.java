package app;

public class ManualOptimizationStarter {

    public static void main(String[] args) {
        System.out.println("running");
        long timestamp = System.nanoTime();
        timedFunction(args);
        timestamp = System.nanoTime() - timestamp;
        System.out.println("Execution Time : " + timestamp);
    }

    private static void timedFunction(String[] args) {

    }


}
