package rosol.userservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Port {
    private int $;
    @JsonProperty("@enabled")
    private String enabled;

    public int get$() {
        return $;
    }

    public void set$(int $) {
        this.$ = $;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }
}
