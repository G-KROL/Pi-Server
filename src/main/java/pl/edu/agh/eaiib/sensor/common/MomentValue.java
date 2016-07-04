package pl.edu.agh.eaiib.sensor.common;

import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;


public interface MomentValue {

    LocalDateTime getMoment();

    Optional<BigDecimal> getMomentValue();
}
