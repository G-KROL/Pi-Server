package pl.edu.agh.eaiib.sensor.light;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue(SensorValueType.LIGHT_INTENSITY_DISCRIMINATOR)
public class LightIntensitySensorValue extends SensorValue {

    protected LightIntensitySensorValue() {
    }

    public LightIntensitySensorValue(BigDecimal value) {
        super(LocalDateTime.now().toDate(), value);
    }
}
