import java.util.*;

public class Lista {

	 static final int events_to_generation = 100000;
	 static int in_event;
	 static int events;


	 List<TEvent> lista = new LinkedList<>();

	static Lista createList() {

		events = 0;
		in_event = 0;
		return new Lista();
	}

	boolean generateEvent() {

		return events < events_to_generation;
	}

	void addEvent(TEvent event) {

        lista.add(event);
		lista.sort(Comparator.comparing(TEvent::getTime));
	}

	TEvent returnFirstEvent() {

        TEvent event = lista.get(0);
        lista.remove(0);
        lista.sort(Comparator.comparing(TEvent::getTime));
        return event;
	}

	TEvent returnLastOutEvent() {

		return lista.stream()
				.filter(e -> "OUT".equals(e.getTypeEvent()))
                .sorted(Comparator.comparing(TEvent::getTime).reversed())
                .findFirst()
                .orElse(null);
	}

	boolean listIsEmpty() {

		return lista.stream().noneMatch(event ->
				"IN".equals(event.getTypeEvent()) || "OUT".equals(event.getTypeEvent()));
	}

}
