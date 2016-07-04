package pl.edu.agh.eaiib.web.relay;

import pl.edu.agh.eaiib.relay.RelayState;


public class RelayResultDto {

    private RelayState relayState;

    protected RelayResultDto(){}

    public RelayResultDto(RelayState relayState) {
        this.relayState = relayState;
    }

    public RelayState getRelayState() {
        return relayState;
    }
}
