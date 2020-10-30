import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class reentrant {
    public static void main(String[] args){
        ReentrantReadWriteLock x = new ReentrantReadWriteLock();
        Lock r = x.readLock();
        ReentrantReadWriteLock.WriteLock w = x.writeLock();
    }
}
