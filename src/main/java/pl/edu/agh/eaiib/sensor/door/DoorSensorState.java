package pl.edu.agh.eaiib.sensor.door;

import pl.edu.agh.eaiib.sensor.common.state.SensorState;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(SensorStateType.DOOR_DISCRIMINATOR)
public class DoorSensorState extends SensorState {

    protected DoorSensorState(){}

    public DoorSensorState(boolean state) {
        super(state);
    }
}
