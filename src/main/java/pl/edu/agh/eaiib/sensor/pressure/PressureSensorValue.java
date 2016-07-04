package pl.edu.agh.eaiib.sensor.pressure;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue(SensorValueType.PRESSURE_DISCRIMINATOR)
public class PressureSensorValue extends SensorValue {

    protected PressureSensorValue(){}

    public PressureSensorValue(BigDecimal value) {
        super(LocalDateTime.now().toDate(), value);
    }
}
