
package intl.who.eios.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "geoData",
    "iso2",
    "whoRegionCode",
    "continentCode",
    "areas",
    "emmAreaId",
    "emmScore",
    "hasMaxEmmScore",
    "area",
    "trigger"
})
@Generated("jsonschema2pojo")
public class Location {

    @JsonProperty("geoData")
    private GeoData geoData;
    @JsonProperty("iso2")
    private String iso2;
    @JsonProperty("whoRegionCode")
    private String whoRegionCode;
    @JsonProperty("continentCode")
    private String continentCode;
    @JsonProperty("areas")
    private List<String> areas = new ArrayList<String>();
    @JsonProperty("emmAreaId")
    private String emmAreaId;
    @JsonProperty("emmScore")
    private Integer emmScore;
    @JsonProperty("hasMaxEmmScore")
    private Boolean hasMaxEmmScore;
    @JsonProperty("area")
    private Area area;
    @JsonProperty("trigger")
    private String trigger;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("geoData")
    public GeoData getGeoData() {
        return geoData;
    }

    @JsonProperty("geoData")
    public void setGeoData(GeoData geoData) {
        this.geoData = geoData;
    }

    public Location withGeoData(GeoData geoData) {
        this.geoData = geoData;
        return this;
    }

    @JsonProperty("iso2")
    public String getIso2() {
        return iso2;
    }

    @JsonProperty("iso2")
    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public Location withIso2(String iso2) {
        this.iso2 = iso2;
        return this;
    }

    @JsonProperty("whoRegionCode")
    public String getWhoRegionCode() {
        return whoRegionCode;
    }

    @JsonProperty("whoRegionCode")
    public void setWhoRegionCode(String whoRegionCode) {
        this.whoRegionCode = whoRegionCode;
    }

    public Location withWhoRegionCode(String whoRegionCode) {
        this.whoRegionCode = whoRegionCode;
        return this;
    }

    @JsonProperty("continentCode")
    public String getContinentCode() {
        return continentCode;
    }

    @JsonProperty("continentCode")
    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public Location withContinentCode(String continentCode) {
        this.continentCode = continentCode;
        return this;
    }

    @JsonProperty("areas")
    public List<String> getAreas() {
        return areas;
    }

    @JsonProperty("areas")
    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

    public Location withAreas(List<String> areas) {
        this.areas = areas;
        return this;
    }

    @JsonProperty("emmAreaId")
    public String getEmmAreaId() {
        return emmAreaId;
    }

    @JsonProperty("emmAreaId")
    public void setEmmAreaId(String emmAreaId) {
        this.emmAreaId = emmAreaId;
    }

    public Location withEmmAreaId(String emmAreaId) {
        this.emmAreaId = emmAreaId;
        return this;
    }

    @JsonProperty("emmScore")
    public Integer getEmmScore() {
        return emmScore;
    }

    @JsonProperty("emmScore")
    public void setEmmScore(Integer emmScore) {
        this.emmScore = emmScore;
    }

    public Location withEmmScore(Integer emmScore) {
        this.emmScore = emmScore;
        return this;
    }

    @JsonProperty("hasMaxEmmScore")
    public Boolean getHasMaxEmmScore() {
        return hasMaxEmmScore;
    }

    @JsonProperty("hasMaxEmmScore")
    public void setHasMaxEmmScore(Boolean hasMaxEmmScore) {
        this.hasMaxEmmScore = hasMaxEmmScore;
    }

    public Location withHasMaxEmmScore(Boolean hasMaxEmmScore) {
        this.hasMaxEmmScore = hasMaxEmmScore;
        return this;
    }

    @JsonProperty("area")
    public Area getArea() {
        return area;
    }

    @JsonProperty("area")
    public void setArea(Area area) {
        this.area = area;
    }

    public Location withArea(Area area) {
        this.area = area;
        return this;
    }

    @JsonProperty("trigger")
    public String getTrigger() {
        return trigger;
    }

    @JsonProperty("trigger")
    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Location withTrigger(String trigger) {
        this.trigger = trigger;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Location withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Location.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("geoData");
        sb.append('=');
        sb.append(((this.geoData == null)?"<null>":this.geoData));
        sb.append(',');
        sb.append("iso2");
        sb.append('=');
        sb.append(((this.iso2 == null)?"<null>":this.iso2));
        sb.append(',');
        sb.append("whoRegionCode");
        sb.append('=');
        sb.append(((this.whoRegionCode == null)?"<null>":this.whoRegionCode));
        sb.append(',');
        sb.append("continentCode");
        sb.append('=');
        sb.append(((this.continentCode == null)?"<null>":this.continentCode));
        sb.append(',');
        sb.append("areas");
        sb.append('=');
        sb.append(((this.areas == null)?"<null>":this.areas));
        sb.append(',');
        sb.append("emmAreaId");
        sb.append('=');
        sb.append(((this.emmAreaId == null)?"<null>":this.emmAreaId));
        sb.append(',');
        sb.append("emmScore");
        sb.append('=');
        sb.append(((this.emmScore == null)?"<null>":this.emmScore));
        sb.append(',');
        sb.append("hasMaxEmmScore");
        sb.append('=');
        sb.append(((this.hasMaxEmmScore == null)?"<null>":this.hasMaxEmmScore));
        sb.append(',');
        sb.append("area");
        sb.append('=');
        sb.append(((this.area == null)?"<null>":this.area));
        sb.append(',');
        sb.append("trigger");
        sb.append('=');
        sb.append(((this.trigger == null)?"<null>":this.trigger));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.hasMaxEmmScore == null)? 0 :this.hasMaxEmmScore.hashCode()));
        result = ((result* 31)+((this.area == null)? 0 :this.area.hashCode()));
        result = ((result* 31)+((this.emmScore == null)? 0 :this.emmScore.hashCode()));
        result = ((result* 31)+((this.areas == null)? 0 :this.areas.hashCode()));
        result = ((result* 31)+((this.emmAreaId == null)? 0 :this.emmAreaId.hashCode()));
        result = ((result* 31)+((this.trigger == null)? 0 :this.trigger.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.geoData == null)? 0 :this.geoData.hashCode()));
        result = ((result* 31)+((this.iso2 == null)? 0 :this.iso2 .hashCode()));
        result = ((result* 31)+((this.whoRegionCode == null)? 0 :this.whoRegionCode.hashCode()));
        result = ((result* 31)+((this.continentCode == null)? 0 :this.continentCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Location) == false) {
            return false;
        }
        Location rhs = ((Location) other);
        return ((((((((((((this.hasMaxEmmScore == rhs.hasMaxEmmScore)||((this.hasMaxEmmScore!= null)&&this.hasMaxEmmScore.equals(rhs.hasMaxEmmScore)))&&((this.area == rhs.area)||((this.area!= null)&&this.area.equals(rhs.area))))&&((this.emmScore == rhs.emmScore)||((this.emmScore!= null)&&this.emmScore.equals(rhs.emmScore))))&&((this.areas == rhs.areas)||((this.areas!= null)&&this.areas.equals(rhs.areas))))&&((this.emmAreaId == rhs.emmAreaId)||((this.emmAreaId!= null)&&this.emmAreaId.equals(rhs.emmAreaId))))&&((this.trigger == rhs.trigger)||((this.trigger!= null)&&this.trigger.equals(rhs.trigger))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.geoData == rhs.geoData)||((this.geoData!= null)&&this.geoData.equals(rhs.geoData))))&&((this.iso2 == rhs.iso2)||((this.iso2 != null)&&this.iso2 .equals(rhs.iso2))))&&((this.whoRegionCode == rhs.whoRegionCode)||((this.whoRegionCode!= null)&&this.whoRegionCode.equals(rhs.whoRegionCode))))&&((this.continentCode == rhs.continentCode)||((this.continentCode!= null)&&this.continentCode.equals(rhs.continentCode))));
    }

}
