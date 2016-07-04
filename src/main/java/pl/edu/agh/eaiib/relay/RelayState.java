package pl.edu.agh.eaiib.relay;


public enum RelayState {
    ON(false), OFF(true);

    private boolean realState;

    RelayState(boolean realState) {
        this.realState = realState;
    }

    public static RelayState parse(boolean realState) {
        for (RelayState val : RelayState.values()) {
            if (val.realState == realState) {
                return val;
            }
        }
        throw new IllegalStateException("Can't map boolean value into enum value. It shouldn't happened");
    }

    public boolean value() {
        return realState;
    }
}
