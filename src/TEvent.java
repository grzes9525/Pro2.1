class TEvent {

    private String typeEvent;
    private double time;

    public TEvent(String typeEvent, double time) {

        this.typeEvent = typeEvent;
        this.time = time;
    }

    public String getTypeEvent() {

        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {

        this.typeEvent = typeEvent;
    }

    public double getTime() {

        return time;
    }

    public void setTime(double time) {

        this.time = time;
    }
}