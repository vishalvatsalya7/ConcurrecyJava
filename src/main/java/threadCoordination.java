import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class threadCoordination {
    private static class factorialThread extends Thread{
        private long num;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;
        public factorialThread(long num){
            this.num = num;
        }
        public void run(){
            this.result = solve(num);
            this.isFinished = true;
        }
        public BigInteger solve(long n){
            BigInteger res = BigInteger.ONE;
            for(long i = 1;i<=n;i++){
                res = res.multiply(new BigInteger(Long.toString(i)));
            }
            return res;
        }
        public boolean isFinished(){
            return this.isFinished;
        }
        public BigInteger getResult(){
            return this.result;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        List<Long> numbers = Arrays.asList(100000L, 23L, 1234L, 2345L, 4567L, 5676L);
        List<factorialThread> threads = new ArrayList<>();
        for(long num:numbers) {
            threads.add(new factorialThread(num));
        }
        for(Thread t:threads){
            t.setDaemon(true);
            t.start();
        }
        for(Thread t:threads){
            t.join(2000);
        }
        for(int i=0;i<numbers.size();i++){
            factorialThread t = threads.get(i);
            if(t.isFinished()){
               System.out.println("Factorial of "+numbers.get(i)+" is "+t.getResult());
            }
            else{
                System.out.println("The calculation of "+numbers.get(i)+" is still in progress!");
            }
        }
    }
}
