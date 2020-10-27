import java.math.BigInteger;

public class interrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("10000000")));
        t.setDaemon(true);
        t.start();
        Thread.sleep(100);
        t.interrupt();
    }

    private static class BlockingClass implements Runnable{
        @Override
        public void run() {
            try{
                Thread.sleep(50000);
            }
            catch(InterruptedException e){
                System.out.println("Exiting from the thread");
            }
        }
    }

    private static class LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger exponent;
        public LongComputationTask(BigInteger base, BigInteger exponent){
            this.base = base;
            this.exponent = exponent;
        }
        @Override
        public void run() {
            System.out.println(base+"^"+exponent+"="+pow(base, exponent));
        }
        private BigInteger pow(BigInteger base, BigInteger exponent){
            BigInteger result = BigInteger.ONE;
            for(BigInteger i = BigInteger.ZERO; i.compareTo(exponent)!=0; i=i.add(BigInteger.ONE)){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("This computation is taking a lot of time!");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }

}
