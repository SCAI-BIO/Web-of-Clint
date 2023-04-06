
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
    "id",
    "areaType",
    "areaIds",
    "aggregationLevels",
    "fullName",
    "level"
})
@Generated("jsonschema2pojo")
public class Area {

    @JsonProperty("id")
    private String id;
    @JsonProperty("areaType")
    private List<String> areaType = new ArrayList<String>();
    @JsonProperty("areaIds")
    private List<String> areaIds = new ArrayList<String>();
    @JsonProperty("aggregationLevels")
    private AggregationLevels aggregationLevels;
    @JsonProperty("fullName")
    private List<String> fullName = new ArrayList<String>();
    @JsonProperty("level")
    private Integer level;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Area withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("areaType")
    public List<String> getAreaType() {
        return areaType;
    }

    @JsonProperty("areaType")
    public void setAreaType(List<String> areaType) {
        this.areaType = areaType;
    }

    public Area withAreaType(List<String> areaType) {
        this.areaType = areaType;
        return this;
    }

    @JsonProperty("areaIds")
    public List<String> getAreaIds() {
        return areaIds;
    }

    @JsonProperty("areaIds")
    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    public Area withAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
        return this;
    }

    @JsonProperty("aggregationLevels")
    public AggregationLevels getAggregationLevels() {
        return aggregationLevels;
    }

    @JsonProperty("aggregationLevels")
    public void setAggregationLevels(AggregationLevels aggregationLevels) {
        this.aggregationLevels = aggregationLevels;
    }

    public Area withAggregationLevels(AggregationLevels aggregationLevels) {
        this.aggregationLevels = aggregationLevels;
        return this;
    }

    @JsonProperty("fullName")
    public List<String> getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(List<String> fullName) {
        this.fullName = fullName;
    }

    public Area withFullName(List<String> fullName) {
        this.fullName = fullName;
        return this;
    }

    @JsonProperty("level")
    public Integer getLevel() {
        return level;
    }

    @JsonProperty("level")
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Area withLevel(Integer level) {
        this.level = level;
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

    public Area withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Area.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("areaType");
        sb.append('=');
        sb.append(((this.areaType == null)?"<null>":this.areaType));
        sb.append(',');
        sb.append("areaIds");
        sb.append('=');
        sb.append(((this.areaIds == null)?"<null>":this.areaIds));
        sb.append(',');
        sb.append("aggregationLevels");
        sb.append('=');
        sb.append(((this.aggregationLevels == null)?"<null>":this.aggregationLevels));
        sb.append(',');
        sb.append("fullName");
        sb.append('=');
        sb.append(((this.fullName == null)?"<null>":this.fullName));
        sb.append(',');
        sb.append("level");
        sb.append('=');
        sb.append(((this.level == null)?"<null>":this.level));
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
        result = ((result* 31)+((this.level == null)? 0 :this.level.hashCode()));
        result = ((result* 31)+((this.areaType == null)? 0 :this.areaType.hashCode()));
        result = ((result* 31)+((this.fullName == null)? 0 :this.fullName.hashCode()));
        result = ((result* 31)+((this.areaIds == null)? 0 :this.areaIds.hashCode()));
        result = ((result* 31)+((this.aggregationLevels == null)? 0 :this.aggregationLevels.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Area) == false) {
            return false;
        }
        Area rhs = ((Area) other);
        return ((((((((this.level == rhs.level)||((this.level!= null)&&this.level.equals(rhs.level)))&&((this.areaType == rhs.areaType)||((this.areaType!= null)&&this.areaType.equals(rhs.areaType))))&&((this.fullName == rhs.fullName)||((this.fullName!= null)&&this.fullName.equals(rhs.fullName))))&&((this.areaIds == rhs.areaIds)||((this.areaIds!= null)&&this.areaIds.equals(rhs.areaIds))))&&((this.aggregationLevels == rhs.aggregationLevels)||((this.aggregationLevels!= null)&&this.aggregationLevels.equals(rhs.aggregationLevels))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
