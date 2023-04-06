
package intl.who.eios.item;

import java.util.HashMap;
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
    "idLevel0",
    "idLevel1",
    "idLevel2",
    "idLevel3",
    "idLevel4",
    "idLevel5"
})
@Generated("jsonschema2pojo")
public class AggregationLevels {

    @JsonProperty("idLevel0")
    private String idLevel0;
    @JsonProperty("idLevel1")
    private String idLevel1;
    @JsonProperty("idLevel2")
    private String idLevel2;
    @JsonProperty("idLevel3")
    private String idLevel3;
    @JsonProperty("idLevel4")
    private String idLevel4;
    @JsonProperty("idLevel5")
    private String idLevel5;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idLevel0")
    public String getIdLevel0() {
        return idLevel0;
    }

    @JsonProperty("idLevel0")
    public void setIdLevel0(String idLevel0) {
        this.idLevel0 = idLevel0;
    }

    public AggregationLevels withIdLevel0(String idLevel0) {
        this.idLevel0 = idLevel0;
        return this;
    }

    @JsonProperty("idLevel1")
    public String getIdLevel1() {
        return idLevel1;
    }

    @JsonProperty("idLevel1")
    public void setIdLevel1(String idLevel1) {
        this.idLevel1 = idLevel1;
    }

    public AggregationLevels withIdLevel1(String idLevel1) {
        this.idLevel1 = idLevel1;
        return this;
    }

    @JsonProperty("idLevel2")
    public String getIdLevel2() {
        return idLevel2;
    }

    @JsonProperty("idLevel2")
    public void setIdLevel2(String idLevel2) {
        this.idLevel2 = idLevel2;
    }

    public AggregationLevels withIdLevel2(String idLevel2) {
        this.idLevel2 = idLevel2;
        return this;
    }

    @JsonProperty("idLevel3")
    public String getIdLevel3() {
        return idLevel3;
    }

    @JsonProperty("idLevel3")
    public void setIdLevel3(String idLevel3) {
        this.idLevel3 = idLevel3;
    }

    public AggregationLevels withIdLevel3(String idLevel3) {
        this.idLevel3 = idLevel3;
        return this;
    }

    @JsonProperty("idLevel4")
    public String getIdLevel4() {
        return idLevel4;
    }

    @JsonProperty("idLevel4")
    public void setIdLevel4(String idLevel4) {
        this.idLevel4 = idLevel4;
    }

    public AggregationLevels withIdLevel4(String idLevel4) {
        this.idLevel4 = idLevel4;
        return this;
    }

    @JsonProperty("idLevel5")
    public String getIdLevel5() {
        return idLevel5;
    }

    @JsonProperty("idLevel5")
    public void setIdLevel5(String idLevel5) {
        this.idLevel5 = idLevel5;
    }

    public AggregationLevels withIdLevel5(String idLevel5) {
        this.idLevel5 = idLevel5;
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

    public AggregationLevels withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(AggregationLevels.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("idLevel0");
        sb.append('=');
        sb.append(((this.idLevel0 == null)?"<null>":this.idLevel0));
        sb.append(',');
        sb.append("idLevel1");
        sb.append('=');
        sb.append(((this.idLevel1 == null)?"<null>":this.idLevel1));
        sb.append(',');
        sb.append("idLevel2");
        sb.append('=');
        sb.append(((this.idLevel2 == null)?"<null>":this.idLevel2));
        sb.append(',');
        sb.append("idLevel3");
        sb.append('=');
        sb.append(((this.idLevel3 == null)?"<null>":this.idLevel3));
        sb.append(',');
        sb.append("idLevel4");
        sb.append('=');
        sb.append(((this.idLevel4 == null)?"<null>":this.idLevel4));
        sb.append(',');
        sb.append("idLevel5");
        sb.append('=');
        sb.append(((this.idLevel5 == null)?"<null>":this.idLevel5));
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
        result = ((result* 31)+((this.idLevel0 == null)? 0 :this.idLevel0 .hashCode()));
        result = ((result* 31)+((this.idLevel2 == null)? 0 :this.idLevel2 .hashCode()));
        result = ((result* 31)+((this.idLevel1 == null)? 0 :this.idLevel1 .hashCode()));
        result = ((result* 31)+((this.idLevel4 == null)? 0 :this.idLevel4 .hashCode()));
        result = ((result* 31)+((this.idLevel3 == null)? 0 :this.idLevel3 .hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.idLevel5 == null)? 0 :this.idLevel5 .hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AggregationLevels) == false) {
            return false;
        }
        AggregationLevels rhs = ((AggregationLevels) other);
        return ((((((((this.idLevel0 == rhs.idLevel0)||((this.idLevel0 != null)&&this.idLevel0 .equals(rhs.idLevel0)))&&((this.idLevel2 == rhs.idLevel2)||((this.idLevel2 != null)&&this.idLevel2 .equals(rhs.idLevel2))))&&((this.idLevel1 == rhs.idLevel1)||((this.idLevel1 != null)&&this.idLevel1 .equals(rhs.idLevel1))))&&((this.idLevel4 == rhs.idLevel4)||((this.idLevel4 != null)&&this.idLevel4 .equals(rhs.idLevel4))))&&((this.idLevel3 == rhs.idLevel3)||((this.idLevel3 != null)&&this.idLevel3 .equals(rhs.idLevel3))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.idLevel5 == rhs.idLevel5)||((this.idLevel5 != null)&&this.idLevel5 .equals(rhs.idLevel5))));
    }

}
