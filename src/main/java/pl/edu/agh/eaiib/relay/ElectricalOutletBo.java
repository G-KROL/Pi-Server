package pl.edu.agh.eaiib.relay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.*;
import static pl.edu.agh.eaiib.relay.RelayState.parse;


@Service
public class ElectricalOutletBo {

    private ElectricalOutletRelayController controller;

    @Autowired
    public ElectricalOutletBo(ElectricalOutletRelayController controller) {
        this.controller = controller;
    }

    public RelayState state() {
        return parse(controller.state());
    }

    public RelayState change(RelayState state) {
        checkNotNull(state);
        return parse(controller.state(state.value()));
    }
}
