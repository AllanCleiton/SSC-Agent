package com.allancleitonppma.sscagent.infrastructure.dto;
public record AddressPattern(
        String zonePrefix,
        String streetPrefix,
        String levelPrefix
) {
}
