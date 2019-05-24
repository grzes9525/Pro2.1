public class Main {

    public static void main(String[] args) {

        for (int lambda = 5; lambda < 65; lambda += 5) {

            double sum = 0;
            for (int i = 0; i < 10; i++) {
                double systemDelay = new Serwer().start(lambda/10D);
                sum += systemDelay;
            }
            double finalSystemDelay = sum / 10;

            System.out.println(finalSystemDelay);
        }
    }

}