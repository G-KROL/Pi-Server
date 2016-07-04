package pl.edu.agh.eaiib.sensor.common.state;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.DateTimeDto;
import pl.edu.agh.eaiib.sensor.common.value.SensorValueHistoryBo;
import pl.edu.agh.eaiib.common.DateFormat;

import java.util.List;


public abstract class AbstractSensorStateBo implements SensorValueHistoryBo<DateTimeDto> {

    protected abstract BaseSensorStateRepository<? extends SensorState> getRepo();

    @Override
    public List<DateTimeDto> historyByDay(LocalDate day) {
        return getDateTimeImpl(day);
    }

    private List<DateTimeDto> getDateTimeImpl(LocalDate day) {
        LocalDate next = day.plusDays(1);
        List<? extends SensorState> states = getRepo().getByRange(day.toDate(), next.toDate());
        List<DateTimeDto> res = Lists.newArrayList();
        for (SensorState state : states) {
            res.add(new DateTimeDto(LocalDateTime.fromDateFields(state.getStateChange())));
        }
        return res;
    }

    @Override
    public List<String[]> historyLineByDay(LocalDate day) {
        List<DateTimeDto> list = getDateTimeImpl(day);
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
