package pl.edu.agh.eaiib.relay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
class ElectricalOutletRelayController {

    private RelayUnitController relayUnitController;

    @Autowired
    ElectricalOutletRelayController(RelayUnitController relayUnitController) {
        this.relayUnitController = relayUnitController;
    }

    boolean state() {
        return relayUnitController.electricalOutletState();
    }

    boolean state(boolean state) {
        return relayUnitController.electricalOutletState(state);
    }
}
