package com.lc.javase.juc.safe;

import com.lc.javase.other.pojo.PointFinal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleTrackerWithDelegating {
    private final ConcurrentHashMap<Long, PointFinal> locations;

    public VehicleTrackerWithDelegating(Map<Long, PointFinal> locations) {
        this.locations = new ConcurrentHashMap<>(locations);
    }

    public Map<Long, PointFinal> getLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    public PointFinal getLocation(long id) {
        return locations.get(id);
    }

    public void setLocation(long id, int x, int y) {
        if (locations.replace(id, new PointFinal(x, y)) == null) {
            throw new IllegalArgumentException("Invalid vehicle id:" + id);
        }
    }
}
