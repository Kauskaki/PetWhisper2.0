package com.petpawology.petwhisper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetInfoMapper {

    public static Map<String, Object> toMap(PetInfo info) {
        Map<String, Object> map = new HashMap<>();
        map.put("petId", info.getPetName()); // assuming unique name or use UUID
        map.put("petName", info.getPetName());
        map.put("petBreed", info.getPetBreed());
        map.put("petGender", info.getPetGender());
        map.put("petAge", info.getPetAge());
        map.put("visitor", info.getVisitorStatus());
        map.put("notificationTitle", info.getNotificationTitle());
        map.put("notificationDescription", info.getNotificationDescription());
        map.put("petAllergies", info.getPetAllergies());
        map.put("vaccines", serializeVaccines(info));
        map.put("medications", serializeMedications(info));
        map.put("allergiesRecords", serializeAllergies(info));
        return map;
    }

    private static List<Map<String, Object>> serializeVaccines(PetInfo info) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PetInfo.Vaccine v : info.getPet_VaccinesRecords()) {
            Map<String, Object> m = new HashMap<>();
            m.put("name", v.name);
            m.put("expirationDate", v.Expirationdate);
            m.put("effectiveDate", v.EffectiveDate);
            m.put("notes", v.notes);
            list.add(m);
        }
        return list;
    }

    private static List<Map<String, Object>> serializeMedications(PetInfo info) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PetInfo.Medication m : info.getPet_Medications()) {
            Map<String, Object> med = new HashMap<>();
            med.put("name", m.MedName);
            med.put("type", m.Medtype);
            med.put("dosage", m.dosage);
            med.put("expirationDate", m.ExpirationDate);
            med.put("instruction", m.Instruction);
            list.add(med);
        }
        return list;
    }

    private static List<Map<String, Object>> serializeAllergies(PetInfo info) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PetInfo.Allergy a : info.getPet_AllergiesRecords()) {
            Map<String, Object> m = new HashMap<>();
            m.put("name", a.name);
            m.put("notes", a.Notes);
            list.add(m);
        }
        return list;
    }

    public static PetInfo fromMap(Map<String, Object> data) {
        PetInfo info = new PetInfo(
                (String) data.get("petName"),
                (String) data.get("petBreed"),
                (String) data.get("petGender"),
                ((Long) data.get("petAge")).intValue(),
                0,
                data.get("visitor") != null && (boolean) data.get("visitor")
        );

        info.setNotificationTitle((String) data.get("notificationTitle"));
        info.setNotificationDescription((String) data.get("notificationDescription"));

        if (data.get("petAllergies") instanceof List) {
            info.setPet_Allergies(new ArrayList<>((List<String>) data.get("petAllergies")));
        }

        info.setPet_VaccinesRecords(deserializeVaccines((List<Map<String, Object>>) data.get("vaccines")));
        info.setPet_Medications(deserializeMedications((List<Map<String, Object>>) data.get("medications")));
        info.setPet_AllergiesRecords(deserializeAllergies((List<Map<String, Object>>) data.get("allergiesRecords")));

        return info;
    }

    private static List<PetInfo.Vaccine> deserializeVaccines(List<Map<String, Object>> list) {
        List<PetInfo.Vaccine> result = new ArrayList<>();
        for (Map<String, Object> m : list) {
            result.add(new PetInfo.Vaccine(
                    (String) m.get("name"),
                    (String) m.get("expirationDate"),
                    (String) m.get("effectiveDate"),
                    (String) m.get("notes")
            ));
        }
        return result;
    }

    private static List<PetInfo.Medication> deserializeMedications(List<Map<String, Object>> list) {
        List<PetInfo.Medication> result = new ArrayList<>();
        for (Map<String, Object> m : list) {
            result.add(new PetInfo.Medication(
                    (String) m.get("name"),
                    ((Long) m.get("type")).intValue(),
                    (String) m.get("dosage"),
                    (String) m.get("expirationDate"),
                    (String) m.get("instruction")
            ));
        }
        return result;
    }

    private static List<PetInfo.Allergy> deserializeAllergies(List<Map<String, Object>> list) {
        List<PetInfo.Allergy> result = new ArrayList<>();
        for (Map<String, Object> m : list) {
            result.add(new PetInfo.Allergy(
                    (String) m.get("name"),
                    (String) m.get("notes")
            ));
        }
        return result;
    }
}
