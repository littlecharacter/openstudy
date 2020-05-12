package com.lc.javase.juc.safe;

import com.lc.javase.other.pojo.PointMutable;
import com.lc.javase.other.util.BeanCopy;

import java.util.Map;

public class VehicleTrackerWithMonitor {
    private final Map<Long, PointMutable> locations;

    public VehicleTrackerWithMonitor(Map<Long, PointMutable> locations) {
        this.locations = locations;
    }

    public synchronized Map<Long, PointMutable> getLocations() {
        return BeanCopy.copyBeanByJson(locations, Map.class);
    }

    public synchronized PointMutable getLocation(long id) {
        PointMutable location = locations.get(id);
        return location == null ? null : new PointMutable(location);
    }

    public synchronized void setLocation(long id, int x, int y) {
        PointMutable location = locations.get(id);
        if (location == null) {
            throw new IllegalArgumentException("No such id:" + id);
        }
        location.x = x;
        location.y = y;
    }
}
