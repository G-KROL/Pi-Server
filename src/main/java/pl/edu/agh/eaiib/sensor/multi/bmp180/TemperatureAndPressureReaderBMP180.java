package pl.edu.agh.eaiib.sensor.multi.bmp180;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

import java.io.IOException;
import java.math.BigDecimal;

public class TemperatureAndPressureReaderBMP180 {

    private I2CDevice device;
    private int ac1;
    private int ac2;
    private int ac3;
    private int ac4;
    private int ac5;
    private int ac6;
    private int b1;
    private int b2;
    private long b5;
    @SuppressWarnings("unused")
    private int mb;
    private int mc;
    private int md;
    private int oss;

    public TemperatureAndPressureReaderBMP180(I2CBus bus) throws IOException {
        device = bus.getDevice(0x77);
    }

    void init() {
        try {
            byte[] eepromData = new byte[22];
            device.read(0xAA, eepromData, 0, 22);
            ac1 = readShort(eepromData, 0);
            ac2 = readShort(eepromData, 2);
            ac3 = readShort(eepromData, 4);
            ac4 = readUShort(eepromData, 6);
            ac5 = readUShort(eepromData, 8);
            ac6 = readUShort(eepromData, 10);
            b1 = readShort(eepromData, 12);
            b2 = readShort(eepromData, 14);
            mb = readShort(eepromData, 16);
            mc = readShort(eepromData, 18);
            md = readShort(eepromData, 20);
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
    }

    private void startTemperatureRead() throws IOException {
        device.write(0xf4, (byte) 0x2e);
    }

    private int readUncompensatedTemperature() throws IOException {
        byte[] t = new byte[2];
        int r = device.read(0xf6, t, 0, 2);
        if (r != 2) {
            throw new IOException("Cannot read temperature; r=" + r);
        }
        return readShort(t, 0);
    }

    private int calculateTemperature(int ut) {
        long x1 = (ut - ac6) * ac5;
        x1 = x1 >>> 15;
        long x2 = (mc << 11) / (x1 + md);
        b5 = x1 + x2;
        return (int) ((b5 + 8) >>> 4);
    }

    private int readTemperature() throws IOException {
        startTemperatureRead();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        int ut = readUncompensatedTemperature();
        return calculateTemperature(ut);
    }

    private void startPressureRead() throws IOException {
        device.write(0xf4, (byte) (0x34 + (oss << 6)));
    }

    private int readUncompensatedPressure() throws IOException {
        byte[] p = new byte[3];
        int r = device.read(0xf6, p, 0, 3);
        if (r != 3) {
            throw new IOException("Cannot read pressure; r=" + r);
        }

        return ((p[0] & 0xff) << 16) + ((p[1] & 0xff) << 8) + (p[2] & 0xff) >> (8 - oss);
    }

    private int calculatePressure(int up) {
        //System.out.println("up5=" + up);
        //System.out.println("bp5=" + b5);

        long p;
        long b6 = b5 - 4000;
        //        //System.out.println("bp6=" + b6);

        long x1 = (b2 * ((b6 * b6) >> 12)) >> 11;
        //        //System.out.println("x1=" + x1);

        long x2 = (ac2 * b6) >> 11;
        //        //System.out.println("x2=" + x2);

        long x3 = x1 + x2;
        //        //System.out.println("x3=" + x3);

        long b3 = (((ac1 * 4 + x3) << oss) + 2) >> 2;
        //        //System.out.println("b3=" + b3);

        x1 = (ac3 * b6) >> 13;
        ////System.out.println("x1=" + x1);

        x2 = (b1 * ((b6 * b6) >> 12)) >> 16;
        //System.out.println("x3=" + x2);

        x3 = ((x1 + x2) + 2) >> 2;
        //System.out.println("x2=" + x3);

        long b4 = (ac4 * (x3 + 32768)) >> 15;
        //System.out.println("b4=" + b4);

        long b7 = (up - b3) * (50000 >> oss);
        //System.out.println("b7=" + b7);

        if (b7 < 0x80000000) {
            p = (b7 * 2) / b4;
        } else {
            p = (b7 / b4) * 2;
        }
        //System.out.println("p=" + p);

        x1 = (p >> 8) * (p >> 8);
        //System.out.println("x1=" + x1);

        x1 = (x1 * 3038) >> 16;
        //System.out.println("x1=" + x1);

        x2 = (-7357 * p) / 65536;
        //System.out.println("x2=" + x2);

        p = p + ((x1 + x2 + 3791) >> 4);
        //System.out.println("p=" + p);

        return (int) p;
    }

    private int readPressure() throws IOException {
        startPressureRead();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        int up = readUncompensatedPressure();
        return calculatePressure(up);
    }

    public BigDecimal readAttitudeMeter() {
        int pressure = 0;
        try {
            pressure = readPressure();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double p0 = 1037;
        double dp = pressure / 100d;
        double power = 1d / 5.255d;
        double division = dp / p0;
        double pw = Math.pow(division, power);

        return new BigDecimal(Double.valueOf(44330 * (1 - pw)).toString());
    }

    private int readShort(byte[] data, int a) {
        return (data[a] * 256) + (data[a + 1] & 0xff);
    }

    private int readUShort(byte[] data, int a) {
        return ((data[a] & 0xff) << 8) + (data[a + 1] & 0xff);
    }

    public BigDecimal readPressurehPa() {
        try {
            return new BigDecimal(Double.valueOf(readPressure() / 100d).toString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public BigDecimal readTemperatureCelsius() {
        try {
            return new BigDecimal(Double.valueOf(readTemperature() / 10).toString());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
