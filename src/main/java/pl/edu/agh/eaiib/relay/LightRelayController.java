package pl.edu.agh.eaiib.relay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
class LightRelayController {

    private RelayUnitController relayUnitController;

    @Autowired
    LightRelayController(RelayUnitController controller) {
        this.relayUnitController = controller;
    }

    boolean state() {
        return relayUnitController.lightState();
    }

    boolean state(boolean state) {
        return relayUnitController.lightState(state);
    }
}
