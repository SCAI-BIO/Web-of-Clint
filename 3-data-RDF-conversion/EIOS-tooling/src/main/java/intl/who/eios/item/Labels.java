
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
    "Not-credible",
    "Credible"
})
@Generated("jsonschema2pojo")
public class Labels {

    @JsonProperty("Not-credible")
    private NotCredible notCredible;
    @JsonProperty("Credible")
    private Credible credible;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Not-credible")
    public NotCredible getNotCredible() {
        return notCredible;
    }

    @JsonProperty("Not-credible")
    public void setNotCredible(NotCredible notCredible) {
        this.notCredible = notCredible;
    }

    public Labels withNotCredible(NotCredible notCredible) {
        this.notCredible = notCredible;
        return this;
    }

    @JsonProperty("Credible")
    public Credible getCredible() {
        return credible;
    }

    @JsonProperty("Credible")
    public void setCredible(Credible credible) {
        this.credible = credible;
    }

    public Labels withCredible(Credible credible) {
        this.credible = credible;
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

    public Labels withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Labels.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("notCredible");
        sb.append('=');
        sb.append(((this.notCredible == null)?"<null>":this.notCredible));
        sb.append(',');
        sb.append("credible");
        sb.append('=');
        sb.append(((this.credible == null)?"<null>":this.credible));
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
        result = ((result* 31)+((this.notCredible == null)? 0 :this.notCredible.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.credible == null)? 0 :this.credible.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Labels) == false) {
            return false;
        }
        Labels rhs = ((Labels) other);
        return ((((this.notCredible == rhs.notCredible)||((this.notCredible!= null)&&this.notCredible.equals(rhs.notCredible)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.credible == rhs.credible)||((this.credible!= null)&&this.credible.equals(rhs.credible))));
    }

}
