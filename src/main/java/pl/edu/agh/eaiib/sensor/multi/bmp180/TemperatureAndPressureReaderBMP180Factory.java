package pl.edu.agh.eaiib.sensor.multi.bmp180;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;


public class TemperatureAndPressureReaderBMP180Factory {

    private TemperatureAndPressureReaderBMP180Factory(){}

    public static TemperatureAndPressureReaderBMP180 create() {
        I2CBus bus;
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            TemperatureAndPressureReaderBMP180 bmp180 = new TemperatureAndPressureReaderBMP180(bus);
            bmp180.init();

            return bmp180;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
