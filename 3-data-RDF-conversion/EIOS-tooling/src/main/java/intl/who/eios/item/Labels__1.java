
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
    "Rumour",
    "Credible",
    "Conspiracy Theory",
    "Extreme Bias",
    "Clickbait",
    "Other Non-credible",
    "Political",
    "Disinformation",
    "Hate News",
    "Satire",
    "Junk Science"
})
@Generated("jsonschema2pojo")
public class Labels__1 {

    @JsonProperty("Rumour")
    private Rumour rumour;
    @JsonProperty("Credible")
    private Credible__1 credible;
    @JsonProperty("Conspiracy Theory")
    private ConspiracyTheory conspiracyTheory;
    @JsonProperty("Extreme Bias")
    private ExtremeBias extremeBias;
    @JsonProperty("Clickbait")
    private Clickbait clickbait;
    @JsonProperty("Other Non-credible")
    private OtherNonCredible otherNonCredible;
    @JsonProperty("Political")
    private Political political;
    @JsonProperty("Disinformation")
    private Disinformation disinformation;
    @JsonProperty("Hate News")
    private HateNews hateNews;
    @JsonProperty("Satire")
    private Satire satire;
    @JsonProperty("Junk Science")
    private JunkScience junkScience;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Rumour")
    public Rumour getRumour() {
        return rumour;
    }

    @JsonProperty("Rumour")
    public void setRumour(Rumour rumour) {
        this.rumour = rumour;
    }

    public Labels__1 withRumour(Rumour rumour) {
        this.rumour = rumour;
        return this;
    }

    @JsonProperty("Credible")
    public Credible__1 getCredible() {
        return credible;
    }

    @JsonProperty("Credible")
    public void setCredible(Credible__1 credible) {
        this.credible = credible;
    }

    public Labels__1 withCredible(Credible__1 credible) {
        this.credible = credible;
        return this;
    }

    @JsonProperty("Conspiracy Theory")
    public ConspiracyTheory getConspiracyTheory() {
        return conspiracyTheory;
    }

    @JsonProperty("Conspiracy Theory")
    public void setConspiracyTheory(ConspiracyTheory conspiracyTheory) {
        this.conspiracyTheory = conspiracyTheory;
    }

    public Labels__1 withConspiracyTheory(ConspiracyTheory conspiracyTheory) {
        this.conspiracyTheory = conspiracyTheory;
        return this;
    }

    @JsonProperty("Extreme Bias")
    public ExtremeBias getExtremeBias() {
        return extremeBias;
    }

    @JsonProperty("Extreme Bias")
    public void setExtremeBias(ExtremeBias extremeBias) {
        this.extremeBias = extremeBias;
    }

    public Labels__1 withExtremeBias(ExtremeBias extremeBias) {
        this.extremeBias = extremeBias;
        return this;
    }

    @JsonProperty("Clickbait")
    public Clickbait getClickbait() {
        return clickbait;
    }

    @JsonProperty("Clickbait")
    public void setClickbait(Clickbait clickbait) {
        this.clickbait = clickbait;
    }

    public Labels__1 withClickbait(Clickbait clickbait) {
        this.clickbait = clickbait;
        return this;
    }

    @JsonProperty("Other Non-credible")
    public OtherNonCredible getOtherNonCredible() {
        return otherNonCredible;
    }

    @JsonProperty("Other Non-credible")
    public void setOtherNonCredible(OtherNonCredible otherNonCredible) {
        this.otherNonCredible = otherNonCredible;
    }

    public Labels__1 withOtherNonCredible(OtherNonCredible otherNonCredible) {
        this.otherNonCredible = otherNonCredible;
        return this;
    }

    @JsonProperty("Political")
    public Political getPolitical() {
        return political;
    }

    @JsonProperty("Political")
    public void setPolitical(Political political) {
        this.political = political;
    }

    public Labels__1 withPolitical(Political political) {
        this.political = political;
        return this;
    }

    @JsonProperty("Disinformation")
    public Disinformation getDisinformation() {
        return disinformation;
    }

    @JsonProperty("Disinformation")
    public void setDisinformation(Disinformation disinformation) {
        this.disinformation = disinformation;
    }

    public Labels__1 withDisinformation(Disinformation disinformation) {
        this.disinformation = disinformation;
        return this;
    }

    @JsonProperty("Hate News")
    public HateNews getHateNews() {
        return hateNews;
    }

    @JsonProperty("Hate News")
    public void setHateNews(HateNews hateNews) {
        this.hateNews = hateNews;
    }

    public Labels__1 withHateNews(HateNews hateNews) {
        this.hateNews = hateNews;
        return this;
    }

    @JsonProperty("Satire")
    public Satire getSatire() {
        return satire;
    }

    @JsonProperty("Satire")
    public void setSatire(Satire satire) {
        this.satire = satire;
    }

    public Labels__1 withSatire(Satire satire) {
        this.satire = satire;
        return this;
    }

    @JsonProperty("Junk Science")
    public JunkScience getJunkScience() {
        return junkScience;
    }

    @JsonProperty("Junk Science")
    public void setJunkScience(JunkScience junkScience) {
        this.junkScience = junkScience;
    }

    public Labels__1 withJunkScience(JunkScience junkScience) {
        this.junkScience = junkScience;
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

    public Labels__1 withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Labels__1 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("rumour");
        sb.append('=');
        sb.append(((this.rumour == null)?"<null>":this.rumour));
        sb.append(',');
        sb.append("credible");
        sb.append('=');
        sb.append(((this.credible == null)?"<null>":this.credible));
        sb.append(',');
        sb.append("conspiracyTheory");
        sb.append('=');
        sb.append(((this.conspiracyTheory == null)?"<null>":this.conspiracyTheory));
        sb.append(',');
        sb.append("extremeBias");
        sb.append('=');
        sb.append(((this.extremeBias == null)?"<null>":this.extremeBias));
        sb.append(',');
        sb.append("clickbait");
        sb.append('=');
        sb.append(((this.clickbait == null)?"<null>":this.clickbait));
        sb.append(',');
        sb.append("otherNonCredible");
        sb.append('=');
        sb.append(((this.otherNonCredible == null)?"<null>":this.otherNonCredible));
        sb.append(',');
        sb.append("political");
        sb.append('=');
        sb.append(((this.political == null)?"<null>":this.political));
        sb.append(',');
        sb.append("disinformation");
        sb.append('=');
        sb.append(((this.disinformation == null)?"<null>":this.disinformation));
        sb.append(',');
        sb.append("hateNews");
        sb.append('=');
        sb.append(((this.hateNews == null)?"<null>":this.hateNews));
        sb.append(',');
        sb.append("satire");
        sb.append('=');
        sb.append(((this.satire == null)?"<null>":this.satire));
        sb.append(',');
        sb.append("junkScience");
        sb.append('=');
        sb.append(((this.junkScience == null)?"<null>":this.junkScience));
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
        result = ((result* 31)+((this.hateNews == null)? 0 :this.hateNews.hashCode()));
        result = ((result* 31)+((this.credible == null)? 0 :this.credible.hashCode()));
        result = ((result* 31)+((this.junkScience == null)? 0 :this.junkScience.hashCode()));
        result = ((result* 31)+((this.political == null)? 0 :this.political.hashCode()));
        result = ((result* 31)+((this.rumour == null)? 0 :this.rumour.hashCode()));
        result = ((result* 31)+((this.extremeBias == null)? 0 :this.extremeBias.hashCode()));
        result = ((result* 31)+((this.conspiracyTheory == null)? 0 :this.conspiracyTheory.hashCode()));
        result = ((result* 31)+((this.clickbait == null)? 0 :this.clickbait.hashCode()));
        result = ((result* 31)+((this.disinformation == null)? 0 :this.disinformation.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.otherNonCredible == null)? 0 :this.otherNonCredible.hashCode()));
        result = ((result* 31)+((this.satire == null)? 0 :this.satire.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Labels__1) == false) {
            return false;
        }
        Labels__1 rhs = ((Labels__1) other);
        return (((((((((((((this.hateNews == rhs.hateNews)||((this.hateNews!= null)&&this.hateNews.equals(rhs.hateNews)))&&((this.credible == rhs.credible)||((this.credible!= null)&&this.credible.equals(rhs.credible))))&&((this.junkScience == rhs.junkScience)||((this.junkScience!= null)&&this.junkScience.equals(rhs.junkScience))))&&((this.political == rhs.political)||((this.political!= null)&&this.political.equals(rhs.political))))&&((this.rumour == rhs.rumour)||((this.rumour!= null)&&this.rumour.equals(rhs.rumour))))&&((this.extremeBias == rhs.extremeBias)||((this.extremeBias!= null)&&this.extremeBias.equals(rhs.extremeBias))))&&((this.conspiracyTheory == rhs.conspiracyTheory)||((this.conspiracyTheory!= null)&&this.conspiracyTheory.equals(rhs.conspiracyTheory))))&&((this.clickbait == rhs.clickbait)||((this.clickbait!= null)&&this.clickbait.equals(rhs.clickbait))))&&((this.disinformation == rhs.disinformation)||((this.disinformation!= null)&&this.disinformation.equals(rhs.disinformation))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.otherNonCredible == rhs.otherNonCredible)||((this.otherNonCredible!= null)&&this.otherNonCredible.equals(rhs.otherNonCredible))))&&((this.satire == rhs.satire)||((this.satire!= null)&&this.satire.equals(rhs.satire))));
    }

}
