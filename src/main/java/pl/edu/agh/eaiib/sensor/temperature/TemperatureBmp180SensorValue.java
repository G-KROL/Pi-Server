package pl.edu.agh.eaiib.sensor.temperature;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue(SensorValueType.TEMPERATURE_BMP180_DISCRIMINATOR)
public class TemperatureBmp180SensorValue extends SensorValue {

    protected TemperatureBmp180SensorValue() {
    }

    public TemperatureBmp180SensorValue(BigDecimal value) {
        super(LocalDateTime.now().toDate(), value);
    }
}
