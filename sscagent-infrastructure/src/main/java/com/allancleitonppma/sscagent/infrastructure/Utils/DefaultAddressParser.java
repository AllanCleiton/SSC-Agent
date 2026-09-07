package com.allancleitonppma.sscagent.infrastructure.Utils;



import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.serviceInterfaces.AddressParser;
import com.allancleitonppma.sscagent.infrastructure.dto.AddressPattern;

import java.util.Map;


public class DefaultAddressParser  implements AddressParser {

    private final AddressPattern pattern;
    private final Map<String, Integer> addressProfiles;

    public DefaultAddressParser(AddressPattern pattern, Map<String, Integer> addressProfile){
        this.addressProfiles = addressProfile;

        this.pattern = pattern;
    }

    @Override
    public Address parse(String original) {

        if (original == null || original.isBlank() || original.equals("SEM ENDEREÇO")) {
            return new Address("xxxxx-xxx-xxx-x","XXXXX", "XXX", "XXX", "X", 0);
        }

        String addressBlock = original.split("-")[0];

        try {

            String levelAndModule = getLevelAndModule(addressBlock);
            //System.out.println(levelAndModule);

            String zone = extractZone(addressBlock);
            String street = extractStreet(addressBlock, zone);
            String level = extractLevel(addressBlock, street);
            String module = extractModule(addressBlock, level);

            return new Address(original, zone, street, module, level, addressProfiles.get(levelAndModule));
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return new Address("xxxxx-xxx-xxx-x","XXXXX", "XXX", "XXX", "X", 0);
        }



    }

    private String extractZone(String value) {

        int streetIndex = value.indexOf(pattern.streetPrefix());

        if (streetIndex <= 0) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a zona: " + value
            );
        }

        return value.substring(0, streetIndex);
    }

    private String extractStreet(String value, String zone) {

        int streetStart = zone.length();

        int levelIndex = value.indexOf(
                pattern.levelPrefix(),
                streetStart
        );

        if (levelIndex <= streetStart) {
            throw new IllegalArgumentException(
                    "Não foi possível identificar a rua: " + value
            );
        }

        return value.substring(streetStart, levelIndex);
    }

    private String extractLevel(String value, String street) {

        int levelStart =
                value.indexOf(
                        pattern.levelPrefix(),
                        street.length()
                );

        int moduleStart = findModuleStart(value, levelStart);

        return value.substring(levelStart, moduleStart);
    }

    private String extractModule(String value, String level) {

        int levelStart = value.indexOf(level);

        return value.substring(levelStart + level.length());
    }

    private int findModuleStart(String value, int levelStart) {

        return levelStart + 3;
    }


    private String getLevelAndModule(String original){

        if (original == null || original.isBlank() || original.equals("SEM ENDEREÇO")) {

            return "XXX";

        }
        //devo implementar uma forma de pegar apenas o 01A de uma String CAM01R15A01A-CAM01-R15-A01-A
        int levelStart = original.indexOf(pattern.levelPrefix());

        return original.substring(9,12);

    }

}