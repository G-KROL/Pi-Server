package pl.edu.agh.eaiib.sensor.movement;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.state.SensorState;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.*;


@Entity
@DiscriminatorValue(SensorStateType.MOVEMENT_DISCRIMINATOR)
public class MovementSensorState extends SensorState {

    protected MovementSensorState() {
    }

    public MovementSensorState(boolean state) {
        super(state);
    }

    @Column(name = "SHOT_NAME",
            unique = true)
    private String shotName;

    public void addShotName(String shotName) {
        this.shotName = checkNotNull(shotName);
    }

    public boolean containShot() {
        return shotName != null;
    }

    public String getShotName() {
        return shotName;
    }

    public String shotNamePrefix() {
        return LocalDateTime.fromDateFields(getStateChange()).toString();
    }
}
