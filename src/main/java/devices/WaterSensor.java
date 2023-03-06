package devices;

public class WaterSensor {

    private long waterLevel;
    private final long maxWaterLevel;      // даётся на вход значение в миллилитрах
    public WaterSensor(long maxWaterLevel) {
        this.maxWaterLevel = maxWaterLevel;
    }

    public boolean isFull() {
        return waterLevel == maxWaterLevel;
    }

    public boolean isEmpty() {
        return waterLevel == 0;
    }

    public void setWaterLevel (long waterLevel) {
        this.waterLevel = waterLevel;
        if (waterLevel < 0 || waterLevel > maxWaterLevel) {
            throw new IllegalArgumentException("Что-то не так с текущим уровнем воды!");
        }
    }
}
