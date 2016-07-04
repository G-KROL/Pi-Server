package pl.edu.agh.eaiib.relay;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.pi4j.io.gpio.PinState.HIGH;
import static com.pi4j.io.gpio.RaspiPin.GPIO_00;
import static com.pi4j.io.gpio.RaspiPin.GPIO_02;
import static java.lang.String.format;


@Component
class RelayUnitController {

    private static final Logger log = LoggerFactory.getLogger(RelayUnitController.class);

    private GpioPinDigitalOutput light;

    private GpioPinDigitalOutput electricalOutlet;

    boolean lightState(boolean state) {
        light.setState(state);
        boolean current = lightState();
        return currentState(state, current);
    }

    boolean electricalOutletState(boolean state) {
        electricalOutlet.setState(state);
        boolean current = electricalOutletState();
        return currentState(state, current);
    }

    private boolean currentState(boolean state, boolean current) {
        if (state != current) {
            log.warn("After trying to set state {} current state still {}", state, current);
        }
        return current;
    }

    boolean lightState() {
        return state(light.getState());
    }

    private boolean state(PinState state) {
        switch (state) {
            case HIGH:
                return true;
            case LOW:
                return false;
            default:
                throw new IllegalStateException(format("Cant parse value: %s", state));
        }
    }

    boolean electricalOutletState() {
        return state(electricalOutlet.getState());
    }

    @PostConstruct
    protected void init() {
        log.debug("Relay unit initialization start...");
        GpioController gpioController = GpioFactory.getInstance();
        this.light = gpioController.provisionDigitalOutputPin(GPIO_00, "Light", HIGH);
        this.electricalOutlet = gpioController.provisionDigitalOutputPin(GPIO_02, "Electrical outlet", HIGH);
        log.debug("Relay unit properly initialized");
    }
}
