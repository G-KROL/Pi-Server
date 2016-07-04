package pl.edu.agh.eaiib.sensor.common;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.ser.LocalDateTimeSerializer;
import com.google.common.base.Optional;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;


public class MomentValueDto implements MomentValue {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime moment;

    private BigDecimal value;

    public MomentValueDto(BigDecimal value) {
        this.value = value;
    }

    public MomentValueDto(LocalDateTime moment, BigDecimal value) {
        this(value);
        this.moment = moment;
    }

    @Override
    public LocalDateTime getMoment() {
        return moment;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Optional<BigDecimal> getMomentValue() {
        return Optional.of(value);
    }
}
