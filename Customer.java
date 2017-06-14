/***
 * Thomas Dang
 * Operating Systems Spring 2017
 * Greg Ozbirn
 */
import java.util.Random;

public class Customer implements Runnable {
    
	public int assignment_Of_Worker, duty, customerNumber; //customer number will be assigned to a duty, and a duty will be assigned to a worker
	public String completedDuties, requestedDuties; //duties that will be assigned to the customer
	public static Thread[] thread1 = new Thread[Semaphore.num_Of_Customers]; //array of threads for Customer objects
	public static Customer[] Customer_Object = new Customer[Semaphore.num_Of_Customers];  //array of Customer objects that are initialized
    
	//constructor that initalizes customer number
	Customer(int num) {
        this.customerNumber = num;
    }
    /*using semaphores on customer threads*/

    @Override
    public void run() {
    	creation_Of_Customer();	
        wait(Semaphore.maximum_Capacity);
        
        /*outputs customer entering shop*/
        System.out.println("customer " + customerNumber + " enters post office"); 
        
        wait(Semaphore.work_Desk);
        wait(Semaphore.mutex_threads);
        enqueue(customerNumber);
        signal(Semaphore.customer_Is_Ready);
        signal(Semaphore.mutex_threads);
        wait(Semaphore.coordinator);
        
        /*outputs customer requesting service and enqueues those duties to worker queue*/
        System.out.println("customer " + customerNumber + " asks postal worker " + assignment_Of_Worker + " to " + requestedDuties);
        Worker.Worker_Object[assignment_Of_Worker].workerQueue.add(duty);
        
        signal(Semaphore.coordinator2 );
        wait(Semaphore.completed[customerNumber]);
        
        /*outputs customer completing work and leaving the post office*/
        System.out.println("customer " + customerNumber + " completed " + completedDuties);
        System.out.println("customer " + customerNumber + " leaves post office");
        
        signal(Semaphore.leave);
        signal(Semaphore.maximum_Capacity);
    }
    
    /*outputs customer creation and gives each of them random duties*/

    void creation_Of_Customer() {
    	int three = 3, one = 1;
        System.out.println("customer " + customerNumber + " created");
        Random random = new Random();
        this.duty = random.nextInt(three) + one;
        
        switch (duty) {
        	case 1:
        		requestedDuties = "buy stamps";
        		break;
        	case 2:
        		requestedDuties = "mail letter";
        		break;
        	case 3:
        		requestedDuties = "mail package";
        		break;
        }
        
        switch (duty) {
            case 1:
                completedDuties = "buying stamps";
                break;
            case 2:
            	completedDuties = "mailing a letter";
                break;
            case 3:
            	completedDuties = "mailing a package";
                break;
        }
        
    }
    
    /*signaling to release thread*/
    void signal(Semaphore sph) {
        sph.release();
    }
    
    /*adds id of customer to the queue*/
    void enqueue(int id) {
    	Semaphore.queueList.add(id);
    }
    
    /*waits to decrement permits*/
    void wait(Semaphore sph) {
        try {
            sph.acquire();
        } catch (InterruptedException e) {

        }
    }  
}