package pl.edu.agh.eaiib.sensor.attitude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.multi.bmp180.TemperatureAndPressureReaderBMP180Factory;
import pl.edu.agh.eaiib.sensor.common.value.AbstractSensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;
import pl.edu.agh.eaiib.sensor.common.value.BaseSensorValueRepository;


@Service
public class AttitudeBo extends AbstractSensorValueBo implements SensorValueBo {

    @Autowired
    private AttitudeSensorValueRepository repository;

    @Override
    public void save() {
        repository.save(SensorValue.attitude(TemperatureAndPressureReaderBMP180Factory.create().readAttitudeMeter()));
    }

    @Override
    protected BaseSensorValueRepository<? extends SensorValue> getRepo() {
        return repository;
    }
}
