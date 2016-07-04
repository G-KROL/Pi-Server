package pl.edu.agh.eaiib.web.sensor;

import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.eaiib.sensor.movement.MovementBo;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.sensor.common.DateTimeDto;
import pl.edu.agh.eaiib.sensor.movement.MovementDto;


@RestController
@RequestMapping("/movement")
public class MoveSensorController extends AbstractHistoricalController<MovementDto> {

    @Autowired
    private MovementBo bo;

    @RequestMapping(value = "/last",
            method = RequestMethod.GET,
            produces = "application/json")
    public DateTimeDto lastMovement() {
        Optional<LocalDateTime> lastMovementOpt = bo.getLastMovement();
        DateTimeDto dto = new DateTimeDto();
        if (lastMovementOpt.isPresent()) {
            dto.setTime(lastMovementOpt.get());
        }
        return dto;
    }

    @Override
    protected SensorValueHistoryBo<MovementDto> getBo() {
        return bo;
    }
}
