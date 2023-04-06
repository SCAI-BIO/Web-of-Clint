
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
    "title",
    "translatedTitle",
    "link",
    "description",
    "translatedDescription",
    "pubDate",
    "language",
    "languageCode",
    "source",
    "fullText",
    "translatedText",
    "tags",
    "affectedCountries",
    "affectedCountriesIso",
    "rssItemId",
    "childIds",
    "triggers",
    "processedOnDate",
    "locations",
    "whoRegionCodes",
    "continentCodes",
    "mentionedPeople",
    "mentionedOrganisations",
    "duplicateId",
    "parentId",
    "emmMisInfo",
    "binaryCredibility",
    "multiCredibility",
    "abstractiveSummary"
})
@Generated("jsonschema2pojo")
public class Item {

    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("translatedTitle")
    private String translatedTitle;
    @JsonProperty("link")
    private String link;
    @JsonProperty("description")
    private String description;
    @JsonProperty("translatedDescription")
    private String translatedDescription;
    @JsonProperty("pubDate")
    private String pubDate;
    @JsonProperty("language")
    private String language;
    @JsonProperty("languageCode")
    private String languageCode;
    @JsonProperty("source")
    private ItemSource source;
    @JsonProperty("fullText")
    private String fullText;
    @JsonProperty("translatedText")
    private String translatedText;
    @JsonProperty("tags")
    private List<String> tags = new ArrayList<String>();
    @JsonProperty("affectedCountries")
    private List<String> affectedCountries = new ArrayList<String>();
    @JsonProperty("affectedCountriesIso")
    private List<String> affectedCountriesIso = new ArrayList<String>();
    @JsonProperty("rssItemId")
    private String rssItemId;
    @JsonProperty("childIds")
    private List<Object> childIds = new ArrayList<Object>();
    @JsonProperty("triggers")
    private List<Trigger> triggers = new ArrayList<Trigger>();
    @JsonProperty("processedOnDate")
    private String processedOnDate;
    @JsonProperty("locations")
    private List<Location> locations = new ArrayList<Location>();
    @JsonProperty("whoRegionCodes")
    private List<String> whoRegionCodes = new ArrayList<String>();
    @JsonProperty("continentCodes")
    private List<String> continentCodes = new ArrayList<String>();
    @JsonProperty("mentionedPeople")
    private MentionedPeople mentionedPeople;
    @JsonProperty("mentionedOrganisations")
    private MentionedOrganisations mentionedOrganisations;
    @JsonProperty("duplicateId")
    private String duplicateId;
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("emmMisInfo")
    private EmmMisInfo emmMisInfo;
    @JsonProperty("binaryCredibility")
    private BinaryCredibility binaryCredibility;
    @JsonProperty("multiCredibility")
    private MultiCredibility multiCredibility;
    @JsonProperty("abstractiveSummary")
    private String abstractiveSummary;
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

    public Item withEntityType(String entityType) {
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

    public Item withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public Item withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("translatedTitle")
    public String getTranslatedTitle() {
        return translatedTitle;
    }

    @JsonProperty("translatedTitle")
    public void setTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    public Item withTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
        return this;
    }

    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    public Item withLink(String link) {
        this.link = link;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Item withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("translatedDescription")
    public String getTranslatedDescription() {
        return translatedDescription;
    }

    @JsonProperty("translatedDescription")
    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }

    public Item withTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
        return this;
    }

    @JsonProperty("pubDate")
    public String getPubDate() {
        return pubDate;
    }

    @JsonProperty("pubDate")
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public Item withPubDate(String pubDate) {
        this.pubDate = pubDate;
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

    public Item withLanguage(String language) {
        this.language = language;
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

    public Item withLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    @JsonProperty("source")
    public ItemSource getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(ItemSource source) {
        this.source = source;
    }

    public Item withSource(ItemSource source) {
        this.source = source;
        return this;
    }

    @JsonProperty("fullText")
    public String getFullText() {
        return fullText;
    }

    @JsonProperty("fullText")
    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public Item withFullText(String fullText) {
        this.fullText = fullText;
        return this;
    }

    @JsonProperty("translatedText")
    public String getTranslatedText() {
        return translatedText;
    }

    @JsonProperty("translatedText")
    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public Item withTranslatedText(String translatedText) {
        this.translatedText = translatedText;
        return this;
    }

    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Item withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    @JsonProperty("affectedCountries")
    public List<String> getAffectedCountries() {
        return affectedCountries;
    }

    @JsonProperty("affectedCountries")
    public void setAffectedCountries(List<String> affectedCountries) {
        this.affectedCountries = affectedCountries;
    }

    public Item withAffectedCountries(List<String> affectedCountries) {
        this.affectedCountries = affectedCountries;
        return this;
    }

    @JsonProperty("affectedCountriesIso")
    public List<String> getAffectedCountriesIso() {
        return affectedCountriesIso;
    }

    @JsonProperty("affectedCountriesIso")
    public void setAffectedCountriesIso(List<String> affectedCountriesIso) {
        this.affectedCountriesIso = affectedCountriesIso;
    }

    public Item withAffectedCountriesIso(List<String> affectedCountriesIso) {
        this.affectedCountriesIso = affectedCountriesIso;
        return this;
    }

    @JsonProperty("rssItemId")
    public String getRssItemId() {
        return rssItemId;
    }

    @JsonProperty("rssItemId")
    public void setRssItemId(String rssItemId) {
        this.rssItemId = rssItemId;
    }

    public Item withRssItemId(String rssItemId) {
        this.rssItemId = rssItemId;
        return this;
    }

    @JsonProperty("childIds")
    public List<Object> getChildIds() {
        return childIds;
    }

    @JsonProperty("childIds")
    public void setChildIds(List<Object> childIds) {
        this.childIds = childIds;
    }

    public Item withChildIds(List<Object> childIds) {
        this.childIds = childIds;
        return this;
    }

    @JsonProperty("triggers")
    public List<Trigger> getTriggers() {
        return triggers;
    }

    @JsonProperty("triggers")
    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public Item withTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
        return this;
    }

    @JsonProperty("processedOnDate")
    public String getProcessedOnDate() {
        return processedOnDate;
    }

    @JsonProperty("processedOnDate")
    public void setProcessedOnDate(String processedOnDate) {
        this.processedOnDate = processedOnDate;
    }

    public Item withProcessedOnDate(String processedOnDate) {
        this.processedOnDate = processedOnDate;
        return this;
    }

    @JsonProperty("locations")
    public List<Location> getLocations() {
        return locations;
    }

    @JsonProperty("locations")
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Item withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }

    @JsonProperty("whoRegionCodes")
    public List<String> getWhoRegionCodes() {
        return whoRegionCodes;
    }

    @JsonProperty("whoRegionCodes")
    public void setWhoRegionCodes(List<String> whoRegionCodes) {
        this.whoRegionCodes = whoRegionCodes;
    }

    public Item withWhoRegionCodes(List<String> whoRegionCodes) {
        this.whoRegionCodes = whoRegionCodes;
        return this;
    }

    @JsonProperty("continentCodes")
    public List<String> getContinentCodes() {
        return continentCodes;
    }

    @JsonProperty("continentCodes")
    public void setContinentCodes(List<String> continentCodes) {
        this.continentCodes = continentCodes;
    }

    public Item withContinentCodes(List<String> continentCodes) {
        this.continentCodes = continentCodes;
        return this;
    }

    @JsonProperty("mentionedPeople")
    public MentionedPeople getMentionedPeople() {
        return mentionedPeople;
    }

    @JsonProperty("mentionedPeople")
    public void setMentionedPeople(MentionedPeople mentionedPeople) {
        this.mentionedPeople = mentionedPeople;
    }

    public Item withMentionedPeople(MentionedPeople mentionedPeople) {
        this.mentionedPeople = mentionedPeople;
        return this;
    }

    @JsonProperty("mentionedOrganisations")
    public MentionedOrganisations getMentionedOrganisations() {
        return mentionedOrganisations;
    }

    @JsonProperty("mentionedOrganisations")
    public void setMentionedOrganisations(MentionedOrganisations mentionedOrganisations) {
        this.mentionedOrganisations = mentionedOrganisations;
    }

    public Item withMentionedOrganisations(MentionedOrganisations mentionedOrganisations) {
        this.mentionedOrganisations = mentionedOrganisations;
        return this;
    }

    @JsonProperty("duplicateId")
    public String getDuplicateId() {
        return duplicateId;
    }

    @JsonProperty("duplicateId")
    public void setDuplicateId(String duplicateId) {
        this.duplicateId = duplicateId;
    }

    public Item withDuplicateId(String duplicateId) {
        this.duplicateId = duplicateId;
        return this;
    }

    @JsonProperty("parentId")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parentId")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Item withParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    @JsonProperty("emmMisInfo")
    public EmmMisInfo getEmmMisInfo() {
        return emmMisInfo;
    }

    @JsonProperty("emmMisInfo")
    public void setEmmMisInfo(EmmMisInfo emmMisInfo) {
        this.emmMisInfo = emmMisInfo;
    }

    public Item withEmmMisInfo(EmmMisInfo emmMisInfo) {
        this.emmMisInfo = emmMisInfo;
        return this;
    }

    @JsonProperty("binaryCredibility")
    public BinaryCredibility getBinaryCredibility() {
        return binaryCredibility;
    }

    @JsonProperty("binaryCredibility")
    public void setBinaryCredibility(BinaryCredibility binaryCredibility) {
        this.binaryCredibility = binaryCredibility;
    }

    public Item withBinaryCredibility(BinaryCredibility binaryCredibility) {
        this.binaryCredibility = binaryCredibility;
        return this;
    }

    @JsonProperty("multiCredibility")
    public MultiCredibility getMultiCredibility() {
        return multiCredibility;
    }

    @JsonProperty("multiCredibility")
    public void setMultiCredibility(MultiCredibility multiCredibility) {
        this.multiCredibility = multiCredibility;
    }

    public Item withMultiCredibility(MultiCredibility multiCredibility) {
        this.multiCredibility = multiCredibility;
        return this;
    }

    @JsonProperty("abstractiveSummary")
    public String getAbstractiveSummary() {
        return abstractiveSummary;
    }

    @JsonProperty("abstractiveSummary")
    public void setAbstractiveSummary(String abstractiveSummary) {
        this.abstractiveSummary = abstractiveSummary;
    }

    public Item withAbstractiveSummary(String abstractiveSummary) {
        this.abstractiveSummary = abstractiveSummary;
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

    public Item withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Item.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("entityType");
        sb.append('=');
        sb.append(((this.entityType == null)?"<null>":this.entityType));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("translatedTitle");
        sb.append('=');
        sb.append(((this.translatedTitle == null)?"<null>":this.translatedTitle));
        sb.append(',');
        sb.append("link");
        sb.append('=');
        sb.append(((this.link == null)?"<null>":this.link));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("translatedDescription");
        sb.append('=');
        sb.append(((this.translatedDescription == null)?"<null>":this.translatedDescription));
        sb.append(',');
        sb.append("pubDate");
        sb.append('=');
        sb.append(((this.pubDate == null)?"<null>":this.pubDate));
        sb.append(',');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("languageCode");
        sb.append('=');
        sb.append(((this.languageCode == null)?"<null>":this.languageCode));
        sb.append(',');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null)?"<null>":this.source));
        sb.append(',');
        sb.append("fullText");
        sb.append('=');
        sb.append(((this.fullText == null)?"<null>":this.fullText));
        sb.append(',');
        sb.append("translatedText");
        sb.append('=');
        sb.append(((this.translatedText == null)?"<null>":this.translatedText));
        sb.append(',');
        sb.append("tags");
        sb.append('=');
        sb.append(((this.tags == null)?"<null>":this.tags));
        sb.append(',');
        sb.append("affectedCountries");
        sb.append('=');
        sb.append(((this.affectedCountries == null)?"<null>":this.affectedCountries));
        sb.append(',');
        sb.append("affectedCountriesIso");
        sb.append('=');
        sb.append(((this.affectedCountriesIso == null)?"<null>":this.affectedCountriesIso));
        sb.append(',');
        sb.append("rssItemId");
        sb.append('=');
        sb.append(((this.rssItemId == null)?"<null>":this.rssItemId));
        sb.append(',');
        sb.append("childIds");
        sb.append('=');
        sb.append(((this.childIds == null)?"<null>":this.childIds));
        sb.append(',');
        sb.append("triggers");
        sb.append('=');
        sb.append(((this.triggers == null)?"<null>":this.triggers));
        sb.append(',');
        sb.append("processedOnDate");
        sb.append('=');
        sb.append(((this.processedOnDate == null)?"<null>":this.processedOnDate));
        sb.append(',');
        sb.append("locations");
        sb.append('=');
        sb.append(((this.locations == null)?"<null>":this.locations));
        sb.append(',');
        sb.append("whoRegionCodes");
        sb.append('=');
        sb.append(((this.whoRegionCodes == null)?"<null>":this.whoRegionCodes));
        sb.append(',');
        sb.append("continentCodes");
        sb.append('=');
        sb.append(((this.continentCodes == null)?"<null>":this.continentCodes));
        sb.append(',');
        sb.append("mentionedPeople");
        sb.append('=');
        sb.append(((this.mentionedPeople == null)?"<null>":this.mentionedPeople));
        sb.append(',');
        sb.append("mentionedOrganisations");
        sb.append('=');
        sb.append(((this.mentionedOrganisations == null)?"<null>":this.mentionedOrganisations));
        sb.append(',');
        sb.append("duplicateId");
        sb.append('=');
        sb.append(((this.duplicateId == null)?"<null>":this.duplicateId));
        sb.append(',');
        sb.append("parentId");
        sb.append('=');
        sb.append(((this.parentId == null)?"<null>":this.parentId));
        sb.append(',');
        sb.append("emmMisInfo");
        sb.append('=');
        sb.append(((this.emmMisInfo == null)?"<null>":this.emmMisInfo));
        sb.append(',');
        sb.append("binaryCredibility");
        sb.append('=');
        sb.append(((this.binaryCredibility == null)?"<null>":this.binaryCredibility));
        sb.append(',');
        sb.append("multiCredibility");
        sb.append('=');
        sb.append(((this.multiCredibility == null)?"<null>":this.multiCredibility));
        sb.append(',');
        sb.append("abstractiveSummary");
        sb.append('=');
        sb.append(((this.abstractiveSummary == null)?"<null>":this.abstractiveSummary));
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
        result = ((result* 31)+((this.emmMisInfo == null)? 0 :this.emmMisInfo.hashCode()));
        result = ((result* 31)+((this.mentionedOrganisations == null)? 0 :this.mentionedOrganisations.hashCode()));
        result = ((result* 31)+((this.translatedTitle == null)? 0 :this.translatedTitle.hashCode()));
        result = ((result* 31)+((this.duplicateId == null)? 0 :this.duplicateId.hashCode()));
        result = ((result* 31)+((this.binaryCredibility == null)? 0 :this.binaryCredibility.hashCode()));
        result = ((result* 31)+((this.link == null)? 0 :this.link.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.translatedDescription == null)? 0 :this.translatedDescription.hashCode()));
        result = ((result* 31)+((this.fullText == null)? 0 :this.fullText.hashCode()));
        result = ((result* 31)+((this.language == null)? 0 :this.language.hashCode()));
        result = ((result* 31)+((this.source == null)? 0 :this.source.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.whoRegionCodes == null)? 0 :this.whoRegionCodes.hashCode()));
        result = ((result* 31)+((this.translatedText == null)? 0 :this.translatedText.hashCode()));
        result = ((result* 31)+((this.childIds == null)? 0 :this.childIds.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.processedOnDate == null)? 0 :this.processedOnDate.hashCode()));
        result = ((result* 31)+((this.entityType == null)? 0 :this.entityType.hashCode()));
        result = ((result* 31)+((this.mentionedPeople == null)? 0 :this.mentionedPeople.hashCode()));
        result = ((result* 31)+((this.languageCode == null)? 0 :this.languageCode.hashCode()));
        result = ((result* 31)+((this.triggers == null)? 0 :this.triggers.hashCode()));
        result = ((result* 31)+((this.pubDate == null)? 0 :this.pubDate.hashCode()));
        result = ((result* 31)+((this.parentId == null)? 0 :this.parentId.hashCode()));
        result = ((result* 31)+((this.abstractiveSummary == null)? 0 :this.abstractiveSummary.hashCode()));
        result = ((result* 31)+((this.tags == null)? 0 :this.tags.hashCode()));
        result = ((result* 31)+((this.affectedCountries == null)? 0 :this.affectedCountries.hashCode()));
        result = ((result* 31)+((this.rssItemId == null)? 0 :this.rssItemId.hashCode()));
        result = ((result* 31)+((this.locations == null)? 0 :this.locations.hashCode()));
        result = ((result* 31)+((this.affectedCountriesIso == null)? 0 :this.affectedCountriesIso.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.multiCredibility == null)? 0 :this.multiCredibility.hashCode()));
        result = ((result* 31)+((this.continentCodes == null)? 0 :this.continentCodes.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Item) == false) {
            return false;
        }
        Item rhs = ((Item) other);
        return (((((((((((((((((((((((((((((((((this.emmMisInfo == rhs.emmMisInfo)||((this.emmMisInfo!= null)&&this.emmMisInfo.equals(rhs.emmMisInfo)))&&((this.mentionedOrganisations == rhs.mentionedOrganisations)||((this.mentionedOrganisations!= null)&&this.mentionedOrganisations.equals(rhs.mentionedOrganisations))))&&((this.translatedTitle == rhs.translatedTitle)||((this.translatedTitle!= null)&&this.translatedTitle.equals(rhs.translatedTitle))))&&((this.duplicateId == rhs.duplicateId)||((this.duplicateId!= null)&&this.duplicateId.equals(rhs.duplicateId))))&&((this.binaryCredibility == rhs.binaryCredibility)||((this.binaryCredibility!= null)&&this.binaryCredibility.equals(rhs.binaryCredibility))))&&((this.link == rhs.link)||((this.link!= null)&&this.link.equals(rhs.link))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.translatedDescription == rhs.translatedDescription)||((this.translatedDescription!= null)&&this.translatedDescription.equals(rhs.translatedDescription))))&&((this.fullText == rhs.fullText)||((this.fullText!= null)&&this.fullText.equals(rhs.fullText))))&&((this.language == rhs.language)||((this.language!= null)&&this.language.equals(rhs.language))))&&((this.source == rhs.source)||((this.source!= null)&&this.source.equals(rhs.source))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.whoRegionCodes == rhs.whoRegionCodes)||((this.whoRegionCodes!= null)&&this.whoRegionCodes.equals(rhs.whoRegionCodes))))&&((this.translatedText == rhs.translatedText)||((this.translatedText!= null)&&this.translatedText.equals(rhs.translatedText))))&&((this.childIds == rhs.childIds)||((this.childIds!= null)&&this.childIds.equals(rhs.childIds))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.processedOnDate == rhs.processedOnDate)||((this.processedOnDate!= null)&&this.processedOnDate.equals(rhs.processedOnDate))))&&((this.entityType == rhs.entityType)||((this.entityType!= null)&&this.entityType.equals(rhs.entityType))))&&((this.mentionedPeople == rhs.mentionedPeople)||((this.mentionedPeople!= null)&&this.mentionedPeople.equals(rhs.mentionedPeople))))&&((this.languageCode == rhs.languageCode)||((this.languageCode!= null)&&this.languageCode.equals(rhs.languageCode))))&&((this.triggers == rhs.triggers)||((this.triggers!= null)&&this.triggers.equals(rhs.triggers))))&&((this.pubDate == rhs.pubDate)||((this.pubDate!= null)&&this.pubDate.equals(rhs.pubDate))))&&((this.parentId == rhs.parentId)||((this.parentId!= null)&&this.parentId.equals(rhs.parentId))))&&((this.abstractiveSummary == rhs.abstractiveSummary)||((this.abstractiveSummary!= null)&&this.abstractiveSummary.equals(rhs.abstractiveSummary))))&&((this.tags == rhs.tags)||((this.tags!= null)&&this.tags.equals(rhs.tags))))&&((this.affectedCountries == rhs.affectedCountries)||((this.affectedCountries!= null)&&this.affectedCountries.equals(rhs.affectedCountries))))&&((this.rssItemId == rhs.rssItemId)||((this.rssItemId!= null)&&this.rssItemId.equals(rhs.rssItemId))))&&((this.locations == rhs.locations)||((this.locations!= null)&&this.locations.equals(rhs.locations))))&&((this.affectedCountriesIso == rhs.affectedCountriesIso)||((this.affectedCountriesIso!= null)&&this.affectedCountriesIso.equals(rhs.affectedCountriesIso))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.multiCredibility == rhs.multiCredibility)||((this.multiCredibility!= null)&&this.multiCredibility.equals(rhs.multiCredibility))))&&((this.continentCodes == rhs.continentCodes)||((this.continentCodes!= null)&&this.continentCodes.equals(rhs.continentCodes))));
    }

}
