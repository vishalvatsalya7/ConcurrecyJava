import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class cas {
    public static void main(String[] args){

    }

    public static class LockFreeStack<T>{
        private AtomicReference<StackNode<T>> head = new AtomicReference<>();
        private AtomicInteger counter = new AtomicInteger(0);
        public void push(T val){
            StackNode<T> newhead = new StackNode<>(val);
            while(true){
                StackNode<T> currentheadnode = head.get();
                newhead.next = currentheadnode;
                if(head.compareAndSet(currentheadnode, newhead)){
                    break;
                }
                else{
                    LockSupport.parkNanos(1);
                }
            }
            counter.incrementAndGet();
        }

        public T pop(){
            StackNode<T> currentheadnode = head.get();
            StackNode<T> newhead;
            while(currentheadnode!=null){
                newhead = currentheadnode.next;
                if(head.compareAndSet(currentheadnode, newhead)){
                    break;
                }
                else{
                    LockSupport.parkNanos(1);
                    currentheadnode = head.get();
                }
            }
            counter.incrementAndGet();
            return currentheadnode!=null ? currentheadnode.value : null;
        }

        public int getCounter(){
            return counter.get();
        }

    }
    private static class StackNode<V>{
        public V value;
        public StackNode<V> next;
        public StackNode(V value){
            this.value = value;
            this.next = next;
        }
    }
}
