package threadtest;

public class SequenceA implements Sequence {

    private static int number = 0;

    @Override
    public int getNumber() {
        number = number + 1;
        return number;
    }

    public static void main(String[] args) {
        Sequence sequence = new SequenceA();

        ClientThread thread = new ClientThread(sequence);
        ClientThread thread1 = new ClientThread(sequence);
        ClientThread thread2 = new ClientThread(sequence);

        thread.start();
        thread1.start();
        thread2.start();
    }
}
