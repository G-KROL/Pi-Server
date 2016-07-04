package pl.edu.agh.eaiib.sensor.temperature.mcp9808;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class TemperatureReaderMCP9808 {

    private static final Logger log = LoggerFactory.getLogger(TemperatureReaderMCP9808.class);

    // This next addresses is returned by "sudo i2cdetect -y 1".
    private final static int I2C_ADDR_DEFAULT = 0x18;
    // Registers
    private final static int TemperatureReaderMCP9808_REG_CONFIG = 0x01;
    private final static int TemperatureReaderMCP9808_REG_UPPER_TEMP = 0x02;
    private final static int TemperatureReaderMCP9808_REG_LOWER_TEMP = 0x03;
    private final static int TemperatureReaderMCP9808_REG_CRIT_TEMP = 0x04;
    private final static int TemperatureReaderMCP9808_REG_AMBIENT_TEMP = 0x05;
    private final static int TemperatureReaderMCP9808_REG_MANUF_ID = 0x06;

    private final static int TemperatureReaderMCP9808_REG_DEVICE_ID = 0x07;
    // Configuration register values.
    private final static int TemperatureReaderMCP9808_REG_CONFIG_SHUTDOWN = 0x0100;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_CRITLOCKED = 0x0080;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_WINLOCKED = 0x0040;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_INTCLR = 0x0020;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_ALERTSTAT = 0x0010;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_ALERTCTRL = 0x0008;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_ALERTSEL = 0x0002;
    private final static int TemperatureReaderMCP9808_REG_CONFIG_ALERTPOL = 0x0002;

    private final static int TemperatureReaderMCP9808_REG_CONFIG_ALERTMODE = 0x0001;

    private I2CDevice mcp9808;

    TemperatureReaderMCP9808() {
        this(I2C_ADDR_DEFAULT);
    }

    private TemperatureReaderMCP9808(int address) {
        try {
            // Get i2c bus
            I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
            // version
            log.debug("Connected to bus. OK.");

            // Get device itself
            mcp9808 = bus.getDevice(address);
            log.debug("Connected to device. OK.");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private int readU16BE(int register) {
        final int TWO = 2;
        byte[] bb = new byte[TWO];
        int nbr;
        try {
            nbr = this.mcp9808.read(register, bb, 0, TWO);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        if (nbr != TWO) {
            throw new IllegalStateException("Cannot read 2 bytes from " + lpad(Integer.toHexString(register), "0", 2));
        }

        log.debug("I2C: 0x" + lpad(Integer.toHexString(bb[0]), "0", 2) + lpad(Integer.toHexString(bb[1]), "0", 2));
        return ((bb[0] & 0xFF) << 8) + (bb[1] & 0xFF);
    }

    public BigDecimal readCelsius() {
        int raw = readU16BE(TemperatureReaderMCP9808_REG_AMBIENT_TEMP);
        float temp = raw & 0x0FFF;
        temp /= 16.0;

        if ((raw & 0x1000) != 0x0) {
            temp -= 256;
        }

        log.debug("DBG: C Temp: " + lpad(Integer.toHexString(raw & 0xFFFF), "0", 4) + ", " + temp);
        return new BigDecimal(Float.valueOf(temp).toString());
    }

    private static String lpad(String s, String with, int len) {
        String str = s;
        while (str.length() < len)
            str = with + str;
        return str;
    }

}
