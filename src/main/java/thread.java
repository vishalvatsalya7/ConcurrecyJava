import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class thread {

    public static final int MAX_PASSWORD = 1000;
    public static void main(String[] args)  {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendningHacker(vault));
        threads.add(new DescendningHacker(vault));
        threads.add(new PoliceThread());

        for(Thread t:threads){
            t.start();
        }
    }

    private static class Vault{
        private int password;
        public Vault(int password){
            this.password = password;
        }
        public boolean isCorrectPassword(int guess){
            try{
                Thread.sleep(5);
            }
            catch (InterruptedException e){}
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread{
        protected Vault vault;
        public HackerThread(Vault vault){
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread : "+this.getName());
            super.start();
        }
    }

    private static class AscendningHacker extends HackerThread{

        public AscendningHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=0;i<MAX_PASSWORD;i++){
                if(vault.isCorrectPassword(i)){
                    System.out.println(this.getName() +" guessed the correct password "+i);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendningHacker extends HackerThread{

        public DescendningHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for(int i=MAX_PASSWORD;i>=0;i--){
                if(vault.isCorrectPassword(i)){
                    System.out.println(this.getName() +" guessed the correct password "+i);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread{

        @Override
        public void run() {
            for(int i=10;i>=0;i--){
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e){}
                System.out.println(i);
            }
            System.out.println("Game over for Hackers!");
            System.exit(0);
        }
    }
}
