import java.util.Objects;

class Serwer {

    private static double lambda;
    private static double czas;
    private static double omited_tevent;
    private static boolean is_available;
    private static double off_time;
    private static double sys_delay;

    private Lista lista;

    double start(double lambda) {

        initialParameters(lambda);

        lista.addEvent(new TEvent("ON", czas));
        lista.addEvent(new TEvent("IN", czas));

        while (!lista.listIsEmpty()) {

            TEvent event = lista.returnFirstEvent();
            czas = event.getTime();
            ++Lista.events;
            handleEvents(event);
        }
        double result = sys_delay / Lista.in_event;
        return result;
    }

    private void handleEvents(TEvent event) {


        if (event.getTypeEvent() == "IN") {

            ++Lista.in_event;

            if (lista.generateEvent()) {
                lista.addEvent(new TEvent("IN", Serwer.czas + Czas.returnIntensity(Serwer.lambda)));
                double serviceTime = Czas.getServiceTime() + Serwer.off_time;
                Serwer.off_time = 0;

                if (Serwer.is_available) {
                    lista.addEvent(new TEvent("OUT", Serwer.czas + serviceTime));
                    Serwer.is_available = false;
                } else {
                    double lastOutEventTime = Objects.isNull(lista.returnLastOutEvent()) ? 0D : lista.returnLastOutEvent().getTime();
                    lista.addEvent(new TEvent("OUT", lastOutEventTime + serviceTime));
                }
                if (checkList()) {
                    Serwer.sys_delay -= event.getTime();
                }
            }
        } else if (event.getTypeEvent() == "OUT") {
            if (lista.returnLastOutEvent() == null) {

                Serwer.is_available = true;
            }

            if (checkList()) {
                Serwer.sys_delay += event.getTime();
            }

        } else if (event.getTypeEvent() == "OFF") {
            Serwer.off_time = Czas.returnSysOff();
            lista.addEvent(new TEvent("ON", Serwer.czas + Serwer.off_time));

        }else if (event.getTypeEvent() == "ON") {
            double systemOnTime = Czas.returnSysOn();
            lista.addEvent(new TEvent("OFF", Serwer.czas + systemOnTime));
        }
    }

    private boolean checkList() {

        return Lista.events > Lista.events_to_generation * Serwer.omited_tevent;
    }

    private void initialParameters(double lambdaValue) {
        lambda = lambdaValue;
        is_available = true;
        czas = 0;
        off_time = 0;
        omited_tevent = 0.18;
        sys_delay = 0;
        lista = Lista.createList();
    }
}