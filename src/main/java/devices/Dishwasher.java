package devices;

import java.util.ArrayList;

public record Dishwasher(long maxWaterLevel) {

    public ArrayList<WaterSensor> createWaterSensor() {
        WaterSensor d1 = new WaterSensor(maxWaterLevel);
        WaterSensor d2 = new WaterSensor(maxWaterLevel);
        ArrayList<WaterSensor> waterSensors = new ArrayList<>();
        waterSensors.add(d1);
        waterSensors.add(d2);
        return waterSensors;
    }

}
