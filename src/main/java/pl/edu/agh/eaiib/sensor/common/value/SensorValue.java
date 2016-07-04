package pl.edu.agh.eaiib.sensor.common.value;

import pl.edu.agh.eaiib.sensor.attitude.AttitudeSensorValue;
import pl.edu.agh.eaiib.sensor.light.LightIntensitySensorValue;
import pl.edu.agh.eaiib.sensor.pressure.PressureSensorValue;
import pl.edu.agh.eaiib.sensor.temperature.TemperatureBmp180SensorValue;
import pl.edu.agh.eaiib.sensor.temperature.TemperatureMcp9808SensorValue;

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
import java.math.BigDecimal;
import java.util.Date;

import static pl.edu.agh.eaiib.sensor.common.value.SensorValueType.ATTITUDE;
import static pl.edu.agh.eaiib.sensor.common.value.SensorValueType.LIGHT_INTENSITY;
import static pl.edu.agh.eaiib.sensor.common.value.SensorValueType.PRESSURE;
import static pl.edu.agh.eaiib.sensor.common.value.SensorValueType.TEMPERATURE_BMP180;
import static pl.edu.agh.eaiib.sensor.common.value.SensorValueType.TEMPERATURE_MCP9808;


@Entity
@Table(name = "SENSOR_VALUE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR",
        sequenceName = "SENSOR_VALUE_SEQ",
        allocationSize = 50,
        initialValue = 1000)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "SENSOR_TYPE",
        discriminatorType = DiscriminatorType.STRING)
public abstract class SensorValue implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQUENCE_GENERATOR"
    )
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOMENT",
            nullable = false,
            updatable = false)
    private Date moment;

    @Column(name = "VALUE",
            nullable = false,
            updatable = false)
    private BigDecimal value;

    public static TemperatureBmp180SensorValue temperatureBmp180(BigDecimal value) {
        return (TemperatureBmp180SensorValue) create(TEMPERATURE_BMP180, value);
    }

    public static TemperatureMcp9808SensorValue temperatureMcp9808(BigDecimal value) {
        return (TemperatureMcp9808SensorValue) create(TEMPERATURE_MCP9808, value);
    }

    public static PressureSensorValue pressure(BigDecimal value) {
        return (PressureSensorValue) create(PRESSURE, value);
    }

    public static AttitudeSensorValue attitude(BigDecimal value) {
        return (AttitudeSensorValue) create(ATTITUDE, value);
    }

    public static LightIntensitySensorValue lightIntensity(BigDecimal value) {
        return (LightIntensitySensorValue) create(LIGHT_INTENSITY, value);
    }

    private static SensorValue create(SensorValueType type, BigDecimal value) {
        switch (type) {
            case TEMPERATURE_BMP180:
                return new TemperatureBmp180SensorValue(value);
            case TEMPERATURE_MCP9808:
                return new TemperatureMcp9808SensorValue(value);
            case PRESSURE:
                return new PressureSensorValue(value);
            case ATTITUDE:
                return new AttitudeSensorValue(value);
            case LIGHT_INTENSITY:
                return new LightIntensitySensorValue(value);
            default:
                throw new IllegalStateException("Cant build for such a type: " + type);
        }
    }

    protected SensorValue() {
    }

    protected SensorValue(Date moment, BigDecimal value) {
        this.moment = moment;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public Date getMoment() {
        return moment;
    }

    public BigDecimal getValue() {
        return value;
    }
}
