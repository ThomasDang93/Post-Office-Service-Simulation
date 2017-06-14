
import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
    private int count;
	public static Queue<Integer> queueList = new LinkedList<>(); //queue that holds number of customers waiting to be served
    public static final int num_Of_Customers = 50;	//maximum number of customers that can be served in total
    public static final int num_Of_Workers = 3;	//amount of postal workers present for service
    public static Semaphore customer_Is_Ready = new Semaphore(0); //handles situations where customer is ready for service  
    public static Semaphore work_Desk = new Semaphore(3);	//work desk allows only 3 postal workers at a time
    public static Semaphore coordinator = new Semaphore(0);
    public static Semaphore leave = new Semaphore(0); //handles customers leaving
    public static Semaphore[] completed= new Semaphore[num_Of_Customers];  
    public static Semaphore scale = new Semaphore(1);	//only one scale can be used by a postal worker at a time
    public static Semaphore coordinator2 = new Semaphore(0);
    public static Semaphore maximum_Capacity = new Semaphore(10);	//only 10 customers are allowed at a time
    public static Semaphore mutex_threads = new Semaphore(1); 
    
    /*
     * constructor that takes in semaphore values as arguments for thread handling
     */
    public Semaphore(int i) {
        if (i < 0) 
        	throw new IllegalArgumentException(i + " < 0");
        
        count = i;
    }
   
    /*increments the permits when thread is released.
     * if the number of permits is less than or equal to 0,
     * then the notify function is called and wakes up a thread
     * in the block queue.
     */
    public synchronized void release() {
    	count++;
    	if (count <= 0) {
            this.notify();
        }      
    }
 
    /*decrements the permits when waiting.
     * if the number of permits is less than 0,
     * then the wait function is called which makes the
     * current thread wait in the block queue until the 
     * notify() function is called.
     */
    public synchronized void acquire() throws InterruptedException {
    	count--;
    	if (count < 0) {
            this.wait();
        }    
    }
}
