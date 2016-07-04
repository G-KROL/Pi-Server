package pl.edu.agh.eaiib.sensor.temperature.mcp9808;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class TemperatureReaderMCP9808Factory {

    private TemperatureReaderMCP9808Factory(){}

    public static TemperatureReaderMCP9808 create() {
        try {
            I2CFactory.getInstance(I2CBus.BUS_1);

            return new TemperatureReaderMCP9808();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
