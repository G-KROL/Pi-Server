package pl.edu.agh.eaiib.sensor.movement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.eaiib.camera.CameraBo;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateSaveBo;
import pl.edu.agh.eaiib.sensor.common.state.SensorStateType;

import static pl.edu.agh.eaiib.sensor.common.state.SensorState.high;
import static pl.edu.agh.eaiib.sensor.common.state.SensorState.low;


@Service
public class MovementSaveBo implements SensorStateSaveBo {

    private static final Logger log = LoggerFactory.getLogger(MovementSaveBo.class);
    private static final SensorStateType STATE_TYPE = SensorStateType.MOVEMENT;

    private MovementSensorStateRepository repository;

    private CameraBo cameraBo;

    @Autowired
    public MovementSaveBo(MovementSensorStateRepository repository, CameraBo cameraBo) {
        this.repository = repository;
        this.cameraBo = cameraBo;
    }

    @Transactional
    @Override
    public void save(boolean state) {
        MovementSensorState movementSensorState = (MovementSensorState) (state ? high(STATE_TYPE) : low(STATE_TYPE));
        try {
            movementSensorState.addShotName(cameraBo.takeShot(movementSensorState.shotNamePrefix()));
        } catch (IllegalStateException e) {
            log.warn("Can't get photo, proceeding movement without photo anyway", e);
        }

        repository.save(movementSensorState);
    }
}
