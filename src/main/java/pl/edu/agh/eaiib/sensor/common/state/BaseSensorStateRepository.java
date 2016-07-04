package pl.edu.agh.eaiib.sensor.common.state;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


@NoRepositoryBean
public interface BaseSensorStateRepository<T extends SensorState> extends JpaRepository<T, Long> {

    @Query(value = "select max(ss.stateChange) from #{#entityName} ss"
            + " where ss.state = true")
    Date getLast();

    @Query("select ss from #{#entityName} ss"
            + " where ss.stateChange >= :f and ss.stateChange < :t and ss.state = true"
            + " order by ss.stateChange desc")
    List<T> getByRange(
            @Param("f")
            Date from,
            @Param("t")
            Date to);
}
