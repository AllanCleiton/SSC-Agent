package com.allancleitonppma.sscagent.infrastructure.dto;

import java.util.HashMap;
import java.util.Map;

public class IdIntegration {
    static Map<String , String> integrationIds = new HashMap<>();


    public IdIntegration(){
        integrationIds.put("200101","5009");
        integrationIds.put("200131","5015");
        integrationIds.put("200020","3902");
        integrationIds.put("11046","11046");
        integrationIds.put("11076","11076");
        integrationIds.put("11074","11074");
        integrationIds.put("11065","11065");

    }

    public static Map<String , String> getIntegrationIds(){
        return integrationIds;
    }
}



