import java.util.*;

public class Lista {

	 static final int events_to_generation = 100000;
	 static int in_event_count;
	 static int events_count;


	 List<TEvent> lista = new LinkedList<>();

	static Lista create_list() {

		events_count = 0;
		in_event_count = 0;
		return new Lista();
	}

	boolean generate_event() {

		return events_count < events_to_generation;
	}

	void add_event(TEvent event) {

        lista.add(event);
		lista.sort(Comparator.comparing(TEvent::getTime));
	}

	TEvent return_first_event() {

        TEvent event = lista.get(0);
        lista.remove(0);
        lista.sort(Comparator.comparing(TEvent::getTime));
        return event;
	}

	TEvent return_last_out_event() {

		return lista.stream()
				.filter(e -> "OUT".equals(e.getTypeEvent()))
                .sorted(Comparator.comparing(TEvent::getTime).reversed())
                .findFirst()
                .orElse(null);
	}

	boolean list_is_empty() {

		return lista.stream().noneMatch(event ->
				"IN".equals(event.getTypeEvent()) || "OUT".equals(event.getTypeEvent()));
	}

}
