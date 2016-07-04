package pl.edu.agh.eaiib.web.sensor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.pressure.PressureBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.multi.bmp180.TemperatureAndPressureReaderBMP180Factory;
import pl.edu.agh.eaiib.sensor.common.MomentValueDto;

import java.io.IOException;

@RestController
@RequestMapping("/pressure")
public class PressureController extends AbstractHistoricalController<MomentValueDto> {

    private static final Logger log = LoggerFactory.getLogger(PressureController.class);

    @Autowired
    private PressureBo bo;

    @RequestMapping(value = "/hPa",
            method = RequestMethod.GET)
    public MomentValueDto readHPa() throws IOException, InterruptedException {
        return new MomentValueDto(TemperatureAndPressureReaderBMP180Factory.create().readPressurehPa());
    }

    @Override
    protected SensorValueHistoryBo<MomentValueDto> getBo() {
        return bo;
    }
}
