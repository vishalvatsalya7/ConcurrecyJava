public class synch {
    private static class InventoryClass{
        private int item = 0;
        public void increment(){
            synchronized (this) {
                item++;
            }
            synchronized (this) {
                System.out.println("in increment");
            }
        }
        public void decrement(){
            synchronized (this) {
                item--;
            }
            synchronized (this) {
                System.out.println("in decrement");
            }
        }
    }
    private static class IncThread extends Thread{
        private InventoryClass obj;
        public IncThread(InventoryClass obj){
            this.obj = obj;
        }
        public void run(){
            for(int i=0;i<10;i++)
                obj.increment();
        }
    }
    private static class DecThread extends Thread{
        private InventoryClass obj;
        public DecThread(InventoryClass obj){
            this.obj = obj;
        }
        public void run(){
            for(int i=0;i<10;i++)
                obj.decrement();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        InventoryClass inv = new InventoryClass();
        Thread inct = new IncThread(inv);
        Thread dect = new DecThread(inv);
        inct.start();
        dect.start();
        dect.join();
        inct.join();
        System.out.println("The total items left in inventory is "+inv.item);
    }
}
