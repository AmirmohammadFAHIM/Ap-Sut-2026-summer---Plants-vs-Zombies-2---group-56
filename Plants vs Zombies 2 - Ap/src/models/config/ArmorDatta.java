package models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmorData {
    private String ArmorType;
    private int BaseHealth;
    private List<String> ArmorFlags;
    private String ClassName;
    private String FireLayer;

    public String getArmorType() { return ArmorType; }
    public void setArmorType(String armorType) { ArmorType = armorType; }

    public int getBaseHealth() { return BaseHealth; }
    public void setBaseHealth(int baseHealth) { BaseHealth = baseHealth; }

    public List<String> getArmorFlags() { return ArmorFlags; }
    public void setArmorFlags(List<String> armorFlags) { ArmorFlags = armorFlags; }

    public String getClassName() { return ClassName; }
    public void setClassName(String className) { ClassName = className; }

    public String getFireLayer() { return FireLayer; }
    public void setFireLayer(String fireLayer) { FireLayer = fireLayer; }

    public boolean isMagnetic() {
        return ArmorFlags != null && ArmorFlags.contains("metallic");
    }
}