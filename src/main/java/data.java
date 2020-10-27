public class data {
    private static class SharedClass{
        private volatile int x = 0;
        private volatile int y = 0;
        public void increment(){
            x++;
            y++;
        }
        public void checkForDataRace(){
            if(y>x){
                System.out.println("y > x - Data Race is detected!");
            }
        }
    }
    public static void main(String[] args){
        SharedClass obj = new SharedClass();
        Thread t1 = new Thread(()->{
            for(int i=0;i<Integer.MAX_VALUE;i++){
                obj.increment();
            }
        });
        Thread t2 = new Thread(()->{
            for(int i=0;i<Integer.MAX_VALUE;i++){
                obj.checkForDataRace();
            }
        });
        t1.start();
        t2.start();
    }
}
