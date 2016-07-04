package pl.edu.agh.eaiib.sensor.common.value;

import org.joda.time.LocalDate;

import java.util.List;

/**

 * @param <T> return history container
 */
public interface SensorValueHistoryBo<T> {

    /**
     * @return list of values registered by day from newest to oldest
     */
    List<T> historyByDay(LocalDate day);

    /**
     * @return list of lines when String[] represents single line
     */
    List<String[]> historyLineByDay(LocalDate day);
}
