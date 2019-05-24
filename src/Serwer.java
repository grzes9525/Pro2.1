import java.util.Objects;

class Serwer {

    private static double lambda;
    private static double czas;
    private static double pominiete_eventy;
    private static boolean dostepny;
    private static double czas_wylaczenia;
    private static double opoznienie_systemu;

    private Lista lista;

    double start(double lambda) {

        create_param(lambda);

        lista.add_event(new TEvent("ON", czas));
        lista.add_event(new TEvent("IN", czas));

        while (!lista.list_is_empty()) {

            TEvent event = lista.return_first_event();
            czas = event.getTime();
            ++Lista.events_count;
            handler(event);
        }
        double result = opoznienie_systemu / Lista.in_event_count;
        return result;
    }

    private void handler(TEvent event) {


        if (event.getTypeEvent() == "IN") {

            ++Lista.in_event_count;

            if (lista.generate_event()) {
                lista.add_event(new TEvent("IN", Serwer.czas + Czas.return_intensity(Serwer.lambda)));
                double serviceTime = Czas.getServiceTime() + Serwer.czas_wylaczenia;
                Serwer.czas_wylaczenia = 0;

                if (Serwer.dostepny) {
                    lista.add_event(new TEvent("OUT", Serwer.czas + serviceTime));
                    Serwer.dostepny = false;
                } else {
                    double lastOutEventTime = Objects.isNull(lista.return_last_out_event()) ? 0D : lista.return_last_out_event().getTime();
                    lista.add_event(new TEvent("OUT", lastOutEventTime + serviceTime));
                }
                if (check_list()) {
                    Serwer.opoznienie_systemu -= event.getTime();
                }
            }
        } else if (event.getTypeEvent() == "OUT") {
            if (lista.return_last_out_event() == null) {

                Serwer.dostepny = true;
            }

            if (check_list()) {
                Serwer.opoznienie_systemu += event.getTime();
            }

        } else if (event.getTypeEvent() == "OFF") {
            Serwer.czas_wylaczenia = Czas.return_sys_off();
            lista.add_event(new TEvent("ON", Serwer.czas + Serwer.czas_wylaczenia));

        }else if (event.getTypeEvent() == "ON") {
            double systemOnTime = Czas.return_sys_on();
            lista.add_event(new TEvent("OFF", Serwer.czas + systemOnTime));
        }
    }

    private boolean check_list() {

        return Lista.events_count > Lista.events_to_generation * Serwer.pominiete_eventy;
    }

    private void create_param(double lambdaValue) {
        lambda = lambdaValue;
        dostepny = true;
        czas = 0;
        czas_wylaczenia = 0;
        pominiete_eventy = 0.18;
        opoznienie_systemu = 0;
        lista = Lista.create_list();
    }
}