package com.allancleitonppma.sscagent.infrastructure.dto;

import java.util.Map;

public class AddressProfile {

    private Map<String, Integer> addressProfiles;

    public Map<String, Integer> getAddressProfile() {
        return addressProfiles;
    }

    public void setAddressProfile(Map<String, Integer> profilePikingDefault) {
        this.addressProfiles = profilePikingDefault;
    }
}