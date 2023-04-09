package com.lc.javase.juc.safe;

import com.lc.javase.other.pojo.PointSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleTrackerWithPublishing {
    private final ConcurrentHashMap<Long, PointSafe> locations;

    public VehicleTrackerWithPublishing(Map<Long, PointSafe> locations) {
        this.locations = new ConcurrentHashMap<>(locations);
    }

    public Map<Long, PointSafe> getLocations() {
        return Collections.unmodifiableMap(new HashMap<>(locations));
    }

    public PointSafe getLocation(long id) {
        return locations.get(id);
    }

    public void setLocation(long id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("Invalid vehicle id:" + id);
        }
        locations.get(id).set(x, y);
    }
}
