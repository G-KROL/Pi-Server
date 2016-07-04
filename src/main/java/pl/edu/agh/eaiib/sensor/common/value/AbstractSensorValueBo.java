package pl.edu.agh.eaiib.sensor.common.value;

import com.google.common.collect.Lists;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.MomentValueDto;
import pl.edu.agh.eaiib.common.DateFormat;
import pl.edu.agh.eaiib.common.DecimalFormat;

import java.util.List;


public abstract class AbstractSensorValueBo implements SensorValueHistoryBo<MomentValueDto> {

    protected abstract BaseSensorValueRepository<? extends SensorValue> getRepo();

    @Override
    public List<MomentValueDto> historyByDay(LocalDate day) {
        return getMomentValueImpl(day);
    }

    private List<MomentValueDto> getMomentValueImpl(LocalDate day) {
        LocalDate next = day.plusDays(1);
        List<? extends SensorValue> values = getRepo().getByRange(day.toDate(), next.toDate());
        List<MomentValueDto> result = Lists.newArrayList();
        for (SensorValue value : values) {
            result.add(new MomentValueDto(LocalDateTime.fromDateFields(value.getMoment()), value.getValue()));
        }
        return result;
    }

    @Override
    public List<String[]> historyLineByDay(LocalDate day) {
        List<MomentValueDto> list = getMomentValueImpl(day);
        List<String[]> result = initResultWithHeader();
        for (MomentValueDto dto : list) {
            result.add(new String[] {new DateFormat(dto.getMoment()).printDefault(),
                    new DecimalFormat(dto.getValue()).printDefault()});
        }
        return result;
    }

    private List<String[]> initResultWithHeader() {
        List<String[]> result = Lists.newArrayList();
        result.add(new String[]{"Moment", "Value"});
        return result;
    }
}
