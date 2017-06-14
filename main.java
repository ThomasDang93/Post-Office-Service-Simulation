
public class main{
	
	/***this loop will keep track of total number of customers completed and instantiates a new instance to the semaphore class with 
	0 permits. it will loop until 50 customers has been reached*/
	static {
        for (int i = 0; i < Semaphore.num_Of_Customers; i++) {
        	Semaphore.completed[i] = new Semaphore(0);
        }
	}
	
    public static void main(String args[]) {
   	
        /*creating customer thread*/
    	customerThread();
    	
        /*creating worker thread*/
        workerThread();
        
        /*creating customer joined thread*/
        customerJoinedThread();
       
        System.exit(0);
    }
    
    static void customerThread()
    {
    	for (int i = 0; i < Semaphore.num_Of_Customers; i++) {
            Customer.Customer_Object[i] = new Customer(i);
            Customer.thread1[i] = new Thread(Customer.Customer_Object[i]);
            Customer.thread1[i].start();
        }
    }
    
    static void workerThread()
    {
    	for (int i = 0; i < Semaphore.num_Of_Workers; i++) {
            Worker.Worker_Object[i] = new Worker(i);
            Worker.thread2[i] = new Thread(Worker.Worker_Object[i]);
            Worker.thread2[i].start();
        }
    }
    
    static void customerJoinedThread()
    {
    	 for (int i = 0; i < Semaphore.num_Of_Customers; i++) {
             try {
             	Customer.thread1[i].join();
                 System.out.println("joined customer " + i);
             } catch (InterruptedException e) {

             }
         }
    }
}
