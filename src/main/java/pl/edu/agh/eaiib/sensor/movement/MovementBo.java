package pl.edu.agh.eaiib.sensor.movement;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.agh.eaiib.common.DateFormat;
import pl.edu.agh.eaiib.sensor.common.DateTimeDto;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;

import java.util.Date;
import java.util.List;


@Service
public class MovementBo implements SensorValueHistoryBo<MovementDto> {

    @Value("${app.movement.shots.url}")
    private String shotBaseUrl;

    private MovementSensorStateRepository repository;

    @Autowired
    public MovementBo(MovementSensorStateRepository repository) {
        this.repository = repository;
    }

    public Optional<LocalDateTime> getLastMovement() {
        Optional<Date> lastMovementOpt = Optional.fromNullable(repository.getLast());
        if (lastMovementOpt.isPresent()) {
            return Optional.of(LocalDateTime.fromDateFields(lastMovementOpt.get()));
        }
        return Optional.absent();
    }

    @Override
    public List<MovementDto> historyByDay(LocalDate day) {
        return getDateTimeImpl(day);
    }

    private List<MovementDto> getDateTimeImpl(LocalDate day) {
        LocalDate next = day.plusDays(1);
        List<MovementSensorState> states = repository.getByRange(day.toDate(), next.toDate());
        List<MovementDto> res = Lists.newArrayList();
        for (MovementSensorState state : states) {
            LocalDateTime localDate = LocalDateTime.fromDateFields(state.getStateChange());
            if (state.containShot()) {
                res.add(new MovementDto(localDate, shotBaseUrl + state.getShotName()));
            } else {
                res.add(new MovementDto(localDate));
            }
        }
        return res;
    }

    @Override
    public List<String[]> historyLineByDay(LocalDate day) {
        List<MovementDto> list = getDateTimeImpl(day);
        List<String[]> result = initResultWithHeader();
        for (DateTimeDto dto : list) {
            result.add(new String[] {new DateFormat(dto.getMoment()).printDefault()});
        }
        return result;
    }

    private List<String[]> initResultWithHeader() {
        List<String[]> result = Lists.newArrayList();
        result.add(new String[]{"Moment"});
        return result;
    }
}
