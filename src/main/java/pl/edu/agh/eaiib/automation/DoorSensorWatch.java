package pl.edu.agh.eaiib.automation;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.eaiib.sensor.door.DoorSaveBo;

import javax.annotation.PostConstruct;


@Component
public class DoorSensorWatch {

    private DoorSaveBo saveBo;

    @Autowired
    public DoorSensorWatch(DoorSaveBo saveBo) {
        this.saveBo = saveBo;
    }

    void watch() {
        final GpioController gpio = GpioFactory.getInstance();

        gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "DoorSensor", PinPullResistance.PULL_UP)
                .addListener(new GpioSensorStateChangesListener(saveBo));
    }

    @PostConstruct
    void init() {
        watch();
    }
}
