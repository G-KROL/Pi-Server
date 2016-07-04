package pl.edu.agh.eaiib.sensor.common.state;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.door.DoorSensorState;
import pl.edu.agh.eaiib.sensor.movement.MovementSensorState;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "SENSOR_STATE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR",
        sequenceName = "SENSOR_STATE_SEQ",
        allocationSize = 50,
        initialValue = 1000)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SENSOR_TYPE",
        discriminatorType = DiscriminatorType.STRING)
public abstract class SensorState implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQUENCE_GENERATOR"
    )
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STATE_CHANGE",
            nullable = false,
            updatable = false)
    private Date stateChange;

    @Column(name = "STATE",
            nullable = false,
            updatable = false)
    private boolean state;

    public Long getId() {
        return id;
    }

    public Date getStateChange() {
        return stateChange;
    }

    public boolean isState() {
        return state;
    }

    public static SensorState low(SensorStateType type) {
        return create(type, false);
    }

    public static SensorState high(SensorStateType type) {
        return create(type, true);
    }

    private static SensorState create(SensorStateType type, boolean state) {
        switch (type) {
            case DOOR:
                return new DoorSensorState(state);
            case MOVEMENT:
                return new MovementSensorState(state);
            default:
                throw new IllegalStateException("Cant build for such a type: " + type);
        }
    }

    @Deprecated
    protected SensorState() {
    }

    protected SensorState(boolean state) {
        this.stateChange = LocalDateTime.now().toDate();
        this.state = state;
    }

    @Override
    public String toString() {
        return "SensorState [id=" + id + ", stateChange=" + stateChange + ", state=" + state + "]";
    }
}
