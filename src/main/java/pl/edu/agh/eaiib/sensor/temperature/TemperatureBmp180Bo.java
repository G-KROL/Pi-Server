package pl.edu.agh.eaiib.sensor.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.multi.bmp180.TemperatureAndPressureReaderBMP180Factory;
import pl.edu.agh.eaiib.sensor.common.value.AbstractSensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.BaseSensorValueRepository;


@Service
public class TemperatureBmp180Bo extends AbstractSensorValueBo implements SensorValueBo {

    @Autowired
    private TemperatureBmp180SensorValueRepository repository;

    @Override
    public void save() {
        repository.save(SensorValue
                .temperatureBmp180(TemperatureAndPressureReaderBMP180Factory.create().readTemperatureCelsius()));
    }

    @Override
    protected BaseSensorValueRepository<? extends SensorValue> getRepo() {
        return repository;
    }
}
