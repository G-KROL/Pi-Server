package pl.edu.agh.eaiib.sensor.light;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.light.bh1750.LightSensorReaderBH1750Factory;
import pl.edu.agh.eaiib.sensor.common.value.AbstractSensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValue;


@Service
public class LightIntensityBo extends AbstractSensorValueBo implements SensorValueBo {

    @Autowired
    private LightIntensitySensorValueRepository repository;

    @Override
    public void save() {
        repository.save(SensorValue
                .lightIntensity(LightSensorReaderBH1750Factory.create().read()));
    }

    @Override
    protected LightIntensitySensorValueRepository getRepo() {
        return repository;
    }
}
