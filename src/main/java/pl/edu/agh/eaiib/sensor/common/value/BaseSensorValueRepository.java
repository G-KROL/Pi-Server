package pl.edu.agh.eaiib.sensor.common.value;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


@NoRepositoryBean
public interface BaseSensorValueRepository<T extends SensorValue> extends JpaRepository<T, Long> {

    @Query("select sv from #{#entityName} sv"
            + " where sv.moment >= :f and sv.moment < :t"
            + " order by sv.moment desc")
    List<T> getByRange(
            @Param("f")
            Date from,
            @Param("t")
            Date to);
}
