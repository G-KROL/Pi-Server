package pl.edu.agh.eaiib.sensor.door;

import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.sensor.common.state.AbstractSensorStateBo;
import pl.edu.agh.eaiib.sensor.common.state.SensorState;
import pl.edu.agh.eaiib.sensor.common.state.BaseSensorStateRepository;

import java.util.Date;

@Service
public class DoorBo extends AbstractSensorStateBo {

    @Autowired
    private DoorSensorStateRepository repository;

    public Optional<LocalDateTime> getLastOpen() {
        Optional<Date> lastDoorOpenOpt = Optional.fromNullable(repository.getLast());
        if (lastDoorOpenOpt.isPresent()) {
            return Optional.of(LocalDateTime.fromDateFields(lastDoorOpenOpt.get()));
        }
        return Optional.absent();
    }

    @Override
    protected BaseSensorStateRepository<? extends SensorState> getRepo() {
        return repository;
    }
}
