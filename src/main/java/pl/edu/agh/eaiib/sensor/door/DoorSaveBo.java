package pl.edu.agh.eaiib.sensor.door;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateRepository;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateSaveBo;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateType;

import static pl.edu.agh.eaiib.sensor.common.state.SensorState.high;
import static pl.edu.agh.eaiib.sensor.common.state.SensorState.low;
import static pl.edu.agh.eaiib.sensor.common.state.SensorStateType.DOOR;


@Service
public class DoorSaveBo implements SensorStateSaveBo {

    private static final SensorStateType STATE_TYPE = DOOR;
    private SensorStateRepository repository;

    @Autowired
    public DoorSaveBo(SensorStateRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(boolean state) {
        repository.save(state ? high(STATE_TYPE) : low(STATE_TYPE));
    }
}
