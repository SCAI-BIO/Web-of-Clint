
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
    "names",
    "entityIds",
    "occurrences"
})
@Generated("jsonschema2pojo")
public class MentionedOrganisations {

    @JsonProperty("names")
    private List<String> names = new ArrayList<String>();
    @JsonProperty("entityIds")
    private List<Integer> entityIds = new ArrayList<Integer>();
    @JsonProperty("occurrences")
    private List<Occurrence__1> occurrences = new ArrayList<Occurrence__1>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("names")
    public List<String> getNames() {
        return names;
    }

    @JsonProperty("names")
    public void setNames(List<String> names) {
        this.names = names;
    }

    public MentionedOrganisations withNames(List<String> names) {
        this.names = names;
        return this;
    }

    @JsonProperty("entityIds")
    public List<Integer> getEntityIds() {
        return entityIds;
    }

    @JsonProperty("entityIds")
    public void setEntityIds(List<Integer> entityIds) {
        this.entityIds = entityIds;
    }

    public MentionedOrganisations withEntityIds(List<Integer> entityIds) {
        this.entityIds = entityIds;
        return this;
    }

    @JsonProperty("occurrences")
    public List<Occurrence__1> getOccurrences() {
        return occurrences;
    }

    @JsonProperty("occurrences")
    public void setOccurrences(List<Occurrence__1> occurrences) {
        this.occurrences = occurrences;
    }

    public MentionedOrganisations withOccurrences(List<Occurrence__1> occurrences) {
        this.occurrences = occurrences;
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

    public MentionedOrganisations withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MentionedOrganisations.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("names");
        sb.append('=');
        sb.append(((this.names == null)?"<null>":this.names));
        sb.append(',');
        sb.append("entityIds");
        sb.append('=');
        sb.append(((this.entityIds == null)?"<null>":this.entityIds));
        sb.append(',');
        sb.append("occurrences");
        sb.append('=');
        sb.append(((this.occurrences == null)?"<null>":this.occurrences));
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
        result = ((result* 31)+((this.occurrences == null)? 0 :this.occurrences.hashCode()));
        result = ((result* 31)+((this.names == null)? 0 :this.names.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.entityIds == null)? 0 :this.entityIds.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MentionedOrganisations) == false) {
            return false;
        }
        MentionedOrganisations rhs = ((MentionedOrganisations) other);
        return (((((this.occurrences == rhs.occurrences)||((this.occurrences!= null)&&this.occurrences.equals(rhs.occurrences)))&&((this.names == rhs.names)||((this.names!= null)&&this.names.equals(rhs.names))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.entityIds == rhs.entityIds)||((this.entityIds!= null)&&this.entityIds.equals(rhs.entityIds))));
    }

}
