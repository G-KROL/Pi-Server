package pl.edu.agh.eaiib.sensor.movement;

import org.joda.time.LocalDateTime;
import pl.edu.agh.eaiib.sensor.common.DateTimeDto;


public class MovementDto extends DateTimeDto {

    private String shotUrl;

    public MovementDto(LocalDateTime time) {
        super(time);
    }

    public MovementDto(LocalDateTime time, String shotUrl) {
        super(time);
        this.shotUrl = shotUrl;
    }

    public String getShotUrl() {
        return shotUrl;
    }
}
