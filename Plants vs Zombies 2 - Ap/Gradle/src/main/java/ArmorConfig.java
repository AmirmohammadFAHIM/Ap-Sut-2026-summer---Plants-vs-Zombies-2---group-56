package models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmorConfig {
    private List<String> aliases;
    private ArmorData objdata;

    public List<String> getAliases() { return aliases; }
    public void setAliases(List<String> aliases) { this.aliases = aliases; }

    public ArmorData getObjdata() { return objdata; }
    public void setObjdata(ArmorData objdata) { this.objdata = objdata; }

    public String getPrimaryAlias() {
        return (aliases != null && !aliases.isEmpty()) ? aliases.get(0) : null;
    }
}