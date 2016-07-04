package pl.edu.agh.eaiib.web.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.temperature.TemperatureMcp9808Bo;
import pl.edu.agh.eaiib.sensor.temperature.mcp9808.TemperatureReaderMCP9808;
import pl.edu.agh.eaiib.sensor.temperature.mcp9808.TemperatureReaderMCP9808Factory;
import pl.edu.agh.eaiib.sensor.common.MomentValueDto;

@RestController
@RequestMapping("/temperatureMcp9808")
public class TemperatureMCP9808Controller extends AbstractHistoricalController<MomentValueDto> {

    @Autowired
    private TemperatureMcp9808Bo bo;

    @Override
    protected SensorValueHistoryBo<MomentValueDto> getBo() {
        return bo;
    }

    @RequestMapping(value = "/celsius",
            method = RequestMethod.GET)
    public MomentValueDto readCelsius() throws Exception {

        TemperatureReaderMCP9808 sensor = TemperatureReaderMCP9808Factory.create();
        return new MomentValueDto(sensor.readCelsius());
    }

}
