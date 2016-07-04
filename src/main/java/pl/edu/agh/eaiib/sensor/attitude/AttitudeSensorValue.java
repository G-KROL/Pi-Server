package pl.edu.agh.eaiib.sensor.attitude;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;


@Entity
@DiscriminatorValue(SensorValueType.ATTITUDE_DISCRIMINATOR)
public class AttitudeSensorValue extends SensorValue {

    protected AttitudeSensorValue(){}
    
    public AttitudeSensorValue(BigDecimal value) {
        super(LocalDateTime.now().toDate(), value);
    }
}
