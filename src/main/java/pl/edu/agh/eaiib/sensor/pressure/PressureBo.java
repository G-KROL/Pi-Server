package pl.edu.agh.eaiib.sensor.pressure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.multi.bmp180.TemperatureAndPressureReaderBMP180Factory;
import pl.edu.agh.eaiib.sensor.common.value.AbstractSensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.BaseSensorValueRepository;


@Service
public class PressureBo extends AbstractSensorValueBo implements SensorValueBo {

    @Autowired
    private PressureSensorValueRepository repository;

    @Override
    public void save() {
        repository.save(SensorValue.pressure(TemperatureAndPressureReaderBMP180Factory.create().readPressurehPa()));
    }

    @Override
    protected BaseSensorValueRepository<? extends SensorValue> getRepo() {
        return repository;
    }
}
