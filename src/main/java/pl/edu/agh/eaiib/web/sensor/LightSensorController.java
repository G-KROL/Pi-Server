package pl.edu.agh.eaiib.web.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.light.LightIntensityBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.light.bh1750.LightSensorReaderBH1750;
import pl.edu.agh.eaiib.sensor.light.bh1750.LightSensorReaderBH1750Factory;
import pl.edu.agh.eaiib.sensor.common.MomentValueDto;

@RestController
@RequestMapping("/lightsensor")
public class LightSensorController extends AbstractHistoricalController<MomentValueDto> {

    @Autowired
    private LightIntensityBo bo;

    @RequestMapping(value = "/lx",
            method = RequestMethod.GET)
    public MomentValueDto readLightSensorValue() {

        LightSensorReaderBH1750 lightSensor = LightSensorReaderBH1750Factory.create();

        return new MomentValueDto(lightSensor.read());
    }

    @Override
    protected SensorValueHistoryBo<MomentValueDto> getBo() {
        return bo;
    }
}
