package pl.edu.agh.eaiib.sensor.light.bh1750;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

import java.io.IOException;
import java.math.BigDecimal;

public class LightSensorReaderBH1750 {

    private I2CDevice device;

    LightSensorReaderBH1750(I2CBus bus) throws IOException {
        device = bus.getDevice(0x23);
    }

    void init() {
        try {
            device.write((byte) 0x10);  //11x resolution 120ms

        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }

    public BigDecimal read() {
        byte[] p = new byte[2];

        int r;
        try {
            r = device.read(p, 0, 2);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if (r != 2) {
            throw new IllegalStateException("Read Error; r=" + r);
        }
        return new BigDecimal((p[0] << 8) | p[1]);
    }

}
