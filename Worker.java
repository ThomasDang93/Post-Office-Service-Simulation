
import java.util.LinkedList;
import java.util.Queue;

public class Worker implements Runnable {
	private int customerNumber;
    public int postal_Worker_Number;  
    public static Queue<Integer> workerQueue = new LinkedList<>();	//contains all the duties that a worker is assigned to
    public static Thread[] thread2 = new Thread[Semaphore.num_Of_Workers]; //array of threads for worker objects
    public static Worker[] Worker_Object = new Worker[Semaphore.num_Of_Workers]; //array of worker objects that are initialized
    
    //constructor that initializes worker number
    Worker(int workerNum) {
        this.postal_Worker_Number = workerNum;
    }
    /*using semaphores on worker threads*/

    @Override
    public void run() {
        postal_Worker_Creation();
        while (!false) {
            wait(Semaphore.customer_Is_Ready);
            wait(Semaphore.mutex_threads);
            
            /*removes customer number from queue so that assignment of worker can be initalized to serve that customer***/
            customerNumber = Semaphore.queueList.remove();
            System.out.println("postal worker " + postal_Worker_Number + " is serving customer " + customerNumber);
            Customer.Customer_Object[customerNumber].assignment_Of_Worker = postal_Worker_Number;
            
            signal(Semaphore.mutex_threads);
            signal(Semaphore.coordinator);
            wait(Semaphore.coordinator2 );
            customer_Service();
            signal(Semaphore.completed[customerNumber]);
            wait(Semaphore.leave);
            signal(Semaphore.work_Desk);
        }
    }
    /***this outlines all of a postal worker's current duties. when a thread finishes sleeping, then a message 
     * will say that the worker has finished serving the customer. case 1 and 2 are both the same because they 
     * just involve mailing letters and buying stamps. case 3 is different because it involves mailing a package, which
     * means that a scale must be used. only one postal worker can use this scale*/

    void customer_Service() {
        int duty = workerQueue.remove();
        switch (duty) {
            case 1:
                try {
                    Thread.sleep(100);                 //this is 1000 to make the program run faster simulation purposes
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("postal worker " + postal_Worker_Number + " has finished serving customer " + customerNumber);
                break;
            case 2:
                try {
                    Thread.sleep(150);                 //this is 1500 to make the program run faster simulation purposes
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("postal worker " + postal_Worker_Number + " has finished serving customer " + customerNumber);
                break;
            case 3:
                wait(Semaphore.scale);
                System.out.println("scales in use by postal worker " + postal_Worker_Number);
                try {
                    Thread.sleep(200);                 //this is 2000 to make program run faster for simulation purposes
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("scales released by postal worker " + postal_Worker_Number);
                signal(Semaphore.scale);
                System.out.println("postal worker " + postal_Worker_Number + " finished serving customer " + customerNumber);
                break;
        }
    }
    
    /*signaling to release thread*/
    void signal(Semaphore sph) {
        sph.release();
    }
    
    /*wait to decrement permits*/
    void wait(Semaphore sph) {
        try {
            sph.acquire();
        } catch (InterruptedException e) {

        }
    }
    
    /*outputs postal worker created*/
    void postal_Worker_Creation() {
        System.out.println("postal worker " + postal_Worker_Number + " created");
    }    
}
