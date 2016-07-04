package pl.edu.agh.eaiib.web.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.attitude.AttitudeBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.multi.bmp180.TemperatureAndPressureReaderBMP180Factory;
import pl.edu.agh.eaiib.sensor.common.MomentValueDto;

import java.io.IOException;

@RestController
@RequestMapping("/attitude")
public class AttitudeController extends AbstractHistoricalController<MomentValueDto> {

    private static final Logger log = LoggerFactory.getLogger(AttitudeController.class);

    @Autowired
    private AttitudeBo bo;

    @RequestMapping(value = "/meter",
            method = RequestMethod.GET)
    public MomentValueDto read() throws IOException, InterruptedException {

        return new MomentValueDto(TemperatureAndPressureReaderBMP180Factory.create().readAttitudeMeter());
    }

    @Override
    protected SensorValueHistoryBo<MomentValueDto> getBo() {
        return bo;
    }
}
