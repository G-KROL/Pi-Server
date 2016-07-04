package pl.edu.agh.eaiib.sensor.light.bh1750;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class LightSensorReaderBH1750Factory {

    private LightSensorReaderBH1750Factory() {
    }

    public static LightSensorReaderBH1750 create() {
        I2CBus bus;
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            LightSensorReaderBH1750 bh1750 = new LightSensorReaderBH1750(bus);

            bh1750.init();

            return bh1750;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
