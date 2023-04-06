
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
    "entityType",
    "id",
    "name",
    "optimaSourceId",
    "country",
    "countryIso",
    "subject",
    "url",
    "languageCode",
    "languageFull",
    "countryFull",
    "sourceTags",
    "category",
    "region",
    "type",
    "period",
    "version",
    "state",
    "lastUpdateDate",
    "frequency",
    "continentCode",
    "whoRegionCode",
    "language"
})
@Generated("jsonschema2pojo")
public class ItemSource {

    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("optimaSourceId")
    private String optimaSourceId;
    @JsonProperty("country")
    private String country;
    @JsonProperty("countryIso")
    private String countryIso;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("url")
    private String url;
    @JsonProperty("languageCode")
    private String languageCode;
    @JsonProperty("languageFull")
    private String languageFull;
    @JsonProperty("countryFull")
    private String countryFull;
    @JsonProperty("sourceTags")
    private List<SourceTag> sourceTags = new ArrayList<SourceTag>();
    @JsonProperty("category")
    private String category;
    @JsonProperty("region")
    private String region;
    @JsonProperty("type")
    private String type;
    @JsonProperty("period")
    private String period;
    @JsonProperty("version")
    private Long version;
    @JsonProperty("state")
    private String state;
    @JsonProperty("lastUpdateDate")
    private String lastUpdateDate;
    @JsonProperty("frequency")
    private Integer frequency;
    @JsonProperty("continentCode")
    private String continentCode;
    @JsonProperty("whoRegionCode")
    private String whoRegionCode;
    @JsonProperty("language")
    private String language;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public ItemSource withEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public ItemSource withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ItemSource withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("optimaSourceId")
    public String getOptimaSourceId() {
        return optimaSourceId;
    }

    @JsonProperty("optimaSourceId")
    public void setOptimaSourceId(String optimaSourceId) {
        this.optimaSourceId = optimaSourceId;
    }

    public ItemSource withOptimaSourceId(String optimaSourceId) {
        this.optimaSourceId = optimaSourceId;
        return this;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public ItemSource withCountry(String country) {
        this.country = country;
        return this;
    }

    @JsonProperty("countryIso")
    public String getCountryIso() {
        return countryIso;
    }

    @JsonProperty("countryIso")
    public void setCountryIso(String countryIso) {
        this.countryIso = countryIso;
    }

    public ItemSource withCountryIso(String countryIso) {
        this.countryIso = countryIso;
        return this;
    }

    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ItemSource withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    public ItemSource withUrl(String url) {
        this.url = url;
        return this;
    }

    @JsonProperty("languageCode")
    public String getLanguageCode() {
        return languageCode;
    }

    @JsonProperty("languageCode")
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public ItemSource withLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    @JsonProperty("languageFull")
    public String getLanguageFull() {
        return languageFull;
    }

    @JsonProperty("languageFull")
    public void setLanguageFull(String languageFull) {
        this.languageFull = languageFull;
    }

    public ItemSource withLanguageFull(String languageFull) {
        this.languageFull = languageFull;
        return this;
    }

    @JsonProperty("countryFull")
    public String getCountryFull() {
        return countryFull;
    }

    @JsonProperty("countryFull")
    public void setCountryFull(String countryFull) {
        this.countryFull = countryFull;
    }

    public ItemSource withCountryFull(String countryFull) {
        this.countryFull = countryFull;
        return this;
    }

    @JsonProperty("sourceTags")
    public List<SourceTag> getSourceTags() {
        return sourceTags;
    }

    @JsonProperty("sourceTags")
    public void setSourceTags(List<SourceTag> sourceTags) {
        this.sourceTags = sourceTags;
    }

    public ItemSource withSourceTags(List<SourceTag> sourceTags) {
        this.sourceTags = sourceTags;
        return this;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public ItemSource withCategory(String category) {
        this.category = category;
        return this;
    }

    @JsonProperty("region")
    public String getRegion() {
        return region;
    }

    @JsonProperty("region")
    public void setRegion(String region) {
        this.region = region;
    }

    public ItemSource withRegion(String region) {
        this.region = region;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public ItemSource withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("period")
    public String getPeriod() {
        return period;
    }

    @JsonProperty("period")
    public void setPeriod(String period) {
        this.period = period;
    }

    public ItemSource withPeriod(String period) {
        this.period = period;
        return this;
    }

    @JsonProperty("version")
    public Long getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Long version) {
        this.version = version;
    }

    public ItemSource withVersion(Long version) {
        this.version = version;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public ItemSource withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("lastUpdateDate")
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    @JsonProperty("lastUpdateDate")
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public ItemSource withLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
        return this;
    }

    @JsonProperty("frequency")
    public Integer getFrequency() {
        return frequency;
    }

    @JsonProperty("frequency")
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public ItemSource withFrequency(Integer frequency) {
        this.frequency = frequency;
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

    public ItemSource withContinentCode(String continentCode) {
        this.continentCode = continentCode;
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

    public ItemSource withWhoRegionCode(String whoRegionCode) {
        this.whoRegionCode = whoRegionCode;
        return this;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    public ItemSource withLanguage(String language) {
        this.language = language;
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

    public ItemSource withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ItemSource .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("entityType");
        sb.append('=');
        sb.append(((this.entityType == null)?"<null>":this.entityType));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("optimaSourceId");
        sb.append('=');
        sb.append(((this.optimaSourceId == null)?"<null>":this.optimaSourceId));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("countryIso");
        sb.append('=');
        sb.append(((this.countryIso == null)?"<null>":this.countryIso));
        sb.append(',');
        sb.append("subject");
        sb.append('=');
        sb.append(((this.subject == null)?"<null>":this.subject));
        sb.append(',');
        sb.append("url");
        sb.append('=');
        sb.append(((this.url == null)?"<null>":this.url));
        sb.append(',');
        sb.append("languageCode");
        sb.append('=');
        sb.append(((this.languageCode == null)?"<null>":this.languageCode));
        sb.append(',');
        sb.append("languageFull");
        sb.append('=');
        sb.append(((this.languageFull == null)?"<null>":this.languageFull));
        sb.append(',');
        sb.append("countryFull");
        sb.append('=');
        sb.append(((this.countryFull == null)?"<null>":this.countryFull));
        sb.append(',');
        sb.append("sourceTags");
        sb.append('=');
        sb.append(((this.sourceTags == null)?"<null>":this.sourceTags));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("region");
        sb.append('=');
        sb.append(((this.region == null)?"<null>":this.region));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("period");
        sb.append('=');
        sb.append(((this.period == null)?"<null>":this.period));
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(',');
        sb.append("lastUpdateDate");
        sb.append('=');
        sb.append(((this.lastUpdateDate == null)?"<null>":this.lastUpdateDate));
        sb.append(',');
        sb.append("frequency");
        sb.append('=');
        sb.append(((this.frequency == null)?"<null>":this.frequency));
        sb.append(',');
        sb.append("continentCode");
        sb.append('=');
        sb.append(((this.continentCode == null)?"<null>":this.continentCode));
        sb.append(',');
        sb.append("whoRegionCode");
        sb.append('=');
        sb.append(((this.whoRegionCode == null)?"<null>":this.whoRegionCode));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
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
        result = ((result* 31)+((this.country == null)? 0 :this.country.hashCode()));
        result = ((result* 31)+((this.subject == null)? 0 :this.subject.hashCode()));
        result = ((result* 31)+((this.lastUpdateDate == null)? 0 :this.lastUpdateDate.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.optimaSourceId == null)? 0 :this.optimaSourceId.hashCode()));
        result = ((result* 31)+((this.languageFull == null)? 0 :this.languageFull.hashCode()));
        result = ((result* 31)+((this.frequency == null)? 0 :this.frequency.hashCode()));
        result = ((result* 31)+((this.sourceTags == null)? 0 :this.sourceTags.hashCode()));
        result = ((result* 31)+((this.countryIso == null)? 0 :this.countryIso.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.state == null)? 0 :this.state.hashCode()));
        result = ((result* 31)+((this.continentCode == null)? 0 :this.continentCode.hashCode()));
        result = ((result* 31)+((this.period == null)? 0 :this.period.hashCode()));
        result = ((result* 31)+((this.entityType == null)? 0 :this.entityType.hashCode()));
        result = ((result* 31)+((this.languageCode == null)? 0 :this.languageCode.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+((this.url == null)? 0 :this.url.hashCode()));
        result = ((result* 31)+((this.countryFull == null)? 0 :this.countryFull.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.category == null)? 0 :this.category.hashCode()));
        result = ((result* 31)+((this.region == null)? 0 :this.region.hashCode()));
        result = ((result* 31)+((this.whoRegionCode == null)? 0 :this.whoRegionCode.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ItemSource) == false) {
            return false;
        }
        ItemSource rhs = ((ItemSource) other);
        return (((((((((((((((((((((((((this.country == rhs.country)||((this.country!= null)&&this.country.equals(rhs.country)))&&((this.subject == rhs.subject)||((this.subject!= null)&&this.subject.equals(rhs.subject))))&&((this.lastUpdateDate == rhs.lastUpdateDate)||((this.lastUpdateDate!= null)&&this.lastUpdateDate.equals(rhs.lastUpdateDate))))&&((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.optimaSourceId == rhs.optimaSourceId)||((this.optimaSourceId!= null)&&this.optimaSourceId.equals(rhs.optimaSourceId))))&&((this.languageFull == rhs.languageFull)||((this.languageFull!= null)&&this.languageFull.equals(rhs.languageFull))))&&((this.frequency == rhs.frequency)||((this.frequency!= null)&&this.frequency.equals(rhs.frequency))))&&((this.sourceTags == rhs.sourceTags)||((this.sourceTags!= null)&&this.sourceTags.equals(rhs.sourceTags))))&&((this.countryIso == rhs.countryIso)||((this.countryIso!= null)&&this.countryIso.equals(rhs.countryIso))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.state == rhs.state)||((this.state!= null)&&this.state.equals(rhs.state))))&&((this.continentCode == rhs.continentCode)||((this.continentCode!= null)&&this.continentCode.equals(rhs.continentCode))))&&((this.period == rhs.period)||((this.period!= null)&&this.period.equals(rhs.period))))&&((this.entityType == rhs.entityType)||((this.entityType!= null)&&this.entityType.equals(rhs.entityType))))&&((this.languageCode == rhs.languageCode)||((this.languageCode!= null)&&this.languageCode.equals(rhs.languageCode))))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&((this.url == rhs.url)||((this.url!= null)&&this.url.equals(rhs.url))))&&((this.countryFull == rhs.countryFull)||((this.countryFull!= null)&&this.countryFull.equals(rhs.countryFull))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.category == rhs.category)||((this.category!= null)&&this.category.equals(rhs.category))))&&((this.region == rhs.region)||((this.region!= null)&&this.region.equals(rhs.region))))&&((this.whoRegionCode == rhs.whoRegionCode)||((this.whoRegionCode!= null)&&this.whoRegionCode.equals(rhs.whoRegionCode))));
    }

}
