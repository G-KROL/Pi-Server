package pl.edu.agh.eaiib.automation;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateSaveBo;



class GpioSensorStateChangesListener implements GpioPinListenerDigital {

    private static final Logger log = LoggerFactory.getLogger(GpioSensorStateChangesListener.class);

    private SensorStateSaveBo saveBo;

    public GpioSensorStateChangesListener(SensorStateSaveBo saveBo) {
        this.saveBo = saveBo;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        PinState eventState = event.getState();
        switch (eventState) {
            case LOW:
                log.debug("Ignored state LOW for listener {}", this);
                break;
            case HIGH:
                saveBo.save(true);
                break;
            default:
                throw new IllegalStateException("Unsupported state: " + eventState);
        }
    }
}
