package models.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZombieConfig {
    private List<String> aliases;
    private String objclass;
    private ZombieData objdata;

    public List<String> getAliases() { return aliases; }
    public void setAliases(List<String> aliases) { this.aliases = aliases; }

    public String getObjclass() { return objclass; }
    public void setObjclass(String objclass) { this.objclass = objclass; }

    public ZombieData getObjdata() { return objdata; }
    public void setObjdata(ZombieData objdata) { this.objdata = objdata; }

    public String getPrimaryAlias() {
        return (aliases != null && !aliases.isEmpty()) ? aliases.get(0) : null;
    }
}