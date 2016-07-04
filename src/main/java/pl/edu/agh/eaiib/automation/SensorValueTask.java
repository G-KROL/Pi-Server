package pl.edu.agh.eaiib.automation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueBo;

import java.util.List;

/**
 * Automatic task lunched periodically through given schedule
 
 */
public class SensorValueTask {

    private Logger log = LoggerFactory.getLogger(SensorValueTask.class);

    private List<SensorValueBo> bos;

    @Autowired
    public SensorValueTask(List<SensorValueBo> bos) {
        this.bos = bos;
    }

    public void execute() {
        log.debug("Reading BM180 values");
        for (SensorValueBo bo : bos) {
            bo.save();
        }
    }
}
