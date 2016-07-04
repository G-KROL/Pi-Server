package pl.edu.agh.eaiib.web.sensor;

import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.door.DoorBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.common.DateTimeDto;


@RestController
@RequestMapping("/door")
public class DoorSensorController extends AbstractHistoricalController<DateTimeDto> {

    @Autowired
    private DoorBo bo;

    @RequestMapping(value = "/lastOpen",
            method = RequestMethod.GET)
    public DateTimeDto lastMovement() {
        Optional<LocalDateTime> lastMovementOpt = bo.getLastOpen();
        DateTimeDto dto = new DateTimeDto();
        if (lastMovementOpt.isPresent()) {
            dto.setTime(lastMovementOpt.get());
        }
        return dto;
    }

    @Override
    protected SensorValueHistoryBo<DateTimeDto> getBo() {
        return bo;
    }
}
