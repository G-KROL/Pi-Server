package pl.edu.agh.eaiib.sensor.temperature;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue(SensorValueType.TEMPERATURE_MCP9808_DISCRIMINATOR)
public class TemperatureMcp9808SensorValue extends SensorValue {

    protected TemperatureMcp9808SensorValue() {
    }

    public TemperatureMcp9808SensorValue(BigDecimal value) {
        super(LocalDateTime.now().toDate(), value);
    }
}
