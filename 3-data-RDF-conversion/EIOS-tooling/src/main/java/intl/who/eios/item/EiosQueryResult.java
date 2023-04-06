
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
    "took",
    "timed_out",
    "_shards",
    "hits"
})
@Generated("jsonschema2pojo")
public class EiosQueryResult {

    @JsonProperty("took")
    private Integer took;
    @JsonProperty("timed_out")
    private Boolean timedOut;
    @JsonProperty("_shards")
    private Shards shards;
    @JsonProperty("hits")
    private Hits hits;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("took")
    public Integer getTook() {
        return took;
    }

    @JsonProperty("took")
    public void setTook(Integer took) {
        this.took = took;
    }

    public EiosQueryResult withTook(Integer took) {
        this.took = took;
        return this;
    }

    @JsonProperty("timed_out")
    public Boolean getTimedOut() {
        return timedOut;
    }

    @JsonProperty("timed_out")
    public void setTimedOut(Boolean timedOut) {
        this.timedOut = timedOut;
    }

    public EiosQueryResult withTimedOut(Boolean timedOut) {
        this.timedOut = timedOut;
        return this;
    }

    @JsonProperty("_shards")
    public Shards getShards() {
        return shards;
    }

    @JsonProperty("_shards")
    public void setShards(Shards shards) {
        this.shards = shards;
    }

    public EiosQueryResult withShards(Shards shards) {
        this.shards = shards;
        return this;
    }

    @JsonProperty("hits")
    public Hits getHits() {
        return hits;
    }

    @JsonProperty("hits")
    public void setHits(Hits hits) {
        this.hits = hits;
    }

    public EiosQueryResult withHits(Hits hits) {
        this.hits = hits;
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

    public EiosQueryResult withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EiosQueryResult.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("took");
        sb.append('=');
        sb.append(((this.took == null)?"<null>":this.took));
        sb.append(',');
        sb.append("timedOut");
        sb.append('=');
        sb.append(((this.timedOut == null)?"<null>":this.timedOut));
        sb.append(',');
        sb.append("shards");
        sb.append('=');
        sb.append(((this.shards == null)?"<null>":this.shards));
        sb.append(',');
        sb.append("hits");
        sb.append('=');
        sb.append(((this.hits == null)?"<null>":this.hits));
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
        result = ((result* 31)+((this.hits == null)? 0 :this.hits.hashCode()));
        result = ((result* 31)+((this.took == null)? 0 :this.took.hashCode()));
        result = ((result* 31)+((this.shards == null)? 0 :this.shards.hashCode()));
        result = ((result* 31)+((this.timedOut == null)? 0 :this.timedOut.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EiosQueryResult) == false) {
            return false;
        }
        EiosQueryResult rhs = ((EiosQueryResult) other);
        return ((((((this.hits == rhs.hits)||((this.hits!= null)&&this.hits.equals(rhs.hits)))&&((this.took == rhs.took)||((this.took!= null)&&this.took.equals(rhs.took))))&&((this.shards == rhs.shards)||((this.shards!= null)&&this.shards.equals(rhs.shards))))&&((this.timedOut == rhs.timedOut)||((this.timedOut!= null)&&this.timedOut.equals(rhs.timedOut))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))));
    }

}
