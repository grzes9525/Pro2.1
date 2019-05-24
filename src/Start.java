public class Start {

    public static void main(String[] args) {

        for (int lambda = 5; lambda < 65; lambda += 5) {

            double sum = 0;
            for (int i = 0; i < 10; i++) {
                double systemDelay = new Serwer().start(lambda/10D);
                sum += systemDelay;
            }
            double final_delay = sum / 10;

            System.out.println(final_delay);
        }
    }

}