package codingtasks;
/*
Implement a queue with a fixed size limit.
It must allocate and initialize, only once, all the memory that it will use.
It should provide methods or functions to enqueue and dequeue in a FIFO manner.
It should also give the ability to peek at the next element to be dequeued.
*/
public class QueueImpl {
    private final String[] queue;
    private final int capacity;
    private int tail=0;
    private int face=0;

    // {}{}{}{}{new}{new}{new}{new}{new}{}{}{}

    public QueueImpl(){
        System.out.println("Hi - creating");
        capacity = 10;
        queue = new String[capacity];
    }

    public void enqueue(String element){
        if (face==(capacity)){
            if (queue[0]==null) {
                queue[0] = element;
                System.out.println(element + " enqueued under index 0");
                face=1;
            } else {
                System.out.println("Capacity exceeded");
            }
        } else {
            if (queue[face]==null) {
                queue[face] = element;
                System.out.println(element + " enqueued under index->"+face);
                face++;
            } else {
                System.out.println("Capacity exceeded");
            }
        }

    }
    public String dequeue(){
        try {
            if (queue[tail]!=null)
            return queue[tail];
            else return "Queue empty";
        } finally {
            if (queue[tail]!=null) {
                queue[tail] = null;
                if (tail == (capacity - 1)) {
                    tail = 0;
                } else {
                    tail++;
                }
            }
        }
    }
    public static void main (String[] args) {
        QueueImpl queueImpl = new QueueImpl();
          for (int i=0;i<8;i++){
        queueImpl.enqueue(""+i);
            }
          for (int i=0;i<4;i++){
        System.out.println("Test ="+queueImpl.dequeue());
            }
        for (int i=0;i<5;i++){
            queueImpl.enqueue("Q"+i);
        }
        for (int i=0;i<4;i++){
            System.out.println("Test ="+queueImpl.dequeue());
        }
        for (int i=0;i<10;i++){
            queueImpl.enqueue("K"+i);
        }
        for (int i=0;i<15;i++){
            System.out.println("Test ="+queueImpl.dequeue());
        }
        for (int i=0;i<5;i++){
            queueImpl.enqueue("F"+i);
        }
        for (int i=0;i<7;i++){
            System.out.println("Test ="+queueImpl.dequeue());
        }
        System.out.println("Hello Yuriy!");
    }

}
