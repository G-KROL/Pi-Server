package pl.edu.agh.eaiib.sensor.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.temperature.mcp9808.TemperatureReaderMCP9808Factory;
import pl.edu.agh.eaiib.sensor.common.value.AbstractSensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.BaseSensorValueRepository;


@Service
public class TemperatureMcp9808Bo extends AbstractSensorValueBo implements SensorValueBo {

    @Autowired
    private TemperatureMcp9808SensorValueRepository repository;

    @Override
    public void save() {
        repository.save(SensorValue
                .temperatureMcp9808(TemperatureReaderMCP9808Factory.create().readCelsius()));
    }

    @Override
    protected BaseSensorValueRepository<? extends SensorValue> getRepo() {
        return repository;
    }
}
