package com.bikerapp.web_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MaintenanceElements {
    OIL_CHANGE("Oil change", 12000),
    VALVE_CLEARANCES("Valve clearances regulation", 24000),
    AIR_FILTER("Air filter change", 24000);

    private final String displayName;
    private final int intervalKm;

}
