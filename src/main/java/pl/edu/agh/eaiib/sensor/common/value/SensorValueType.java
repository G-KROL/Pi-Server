package pl.edu.agh.eaiib.sensor.common.value;


public enum SensorValueType {

    TEMPERATURE_BMP180, TEMPERATURE_MCP9808, PRESSURE, ATTITUDE, LIGHT_INTENSITY;
    public static final String TEMPERATURE_BMP180_DISCRIMINATOR = "TEMPERATURE_BMP180";
    public static final String TEMPERATURE_MCP9808_DISCRIMINATOR = "TEMPERATURE_MCP9808";
    public static final String PRESSURE_DISCRIMINATOR = "PRESSURE";
    public static final String ATTITUDE_DISCRIMINATOR = "ATTITUDE";
    public static final String LIGHT_INTENSITY_DISCRIMINATOR = "LIGHT_INTENSITY";
}
