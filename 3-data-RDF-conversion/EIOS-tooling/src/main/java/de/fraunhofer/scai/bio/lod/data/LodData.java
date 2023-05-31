
package de.fraunhofer.scai.bio.lod.data;

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
    "_id",
    "identifier",
    "doi",
    "image",
    "links",
    "keywords",
    "license",
    "title",
    "full_download",
    "example",
    "sparql",
    "other_download",
    "description",
    "owner",
    "website",
    "triples",
    "domain",
    "namespace",
    "contact_point"
})
@Generated("jsonschema2pojo")
public class LodData {

    @JsonProperty("_id")
    private String id;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("doi")
    private String doi;
    @JsonProperty("image")
    private String image;
    @JsonProperty("links")
    private List<Link> links = new ArrayList<Link>();
    @JsonProperty("keywords")
    private List<String> keywords = new ArrayList<String>();
    @JsonProperty("license")
    private String license;
    @JsonProperty("title")
    private String title;
    @JsonProperty("full_download")
    private List<FullDownload> fullDownload = new ArrayList<FullDownload>();
    @JsonProperty("example")
    private List<Example> example = new ArrayList<Example>();
    @JsonProperty("sparql")
    private List<Sparql> sparql = new ArrayList<Sparql>();
    @JsonProperty("other_download")
    private List<Object> otherDownload = new ArrayList<Object>();
    @JsonProperty("description")
    private Description description;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("website")
    private String website;
    @JsonProperty("triples")
    private Integer triples;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("namespace")
    private String namespace;
    @JsonProperty("contact_point")
    private ContactPoint contactPoint;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public LodData withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LodData withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    @JsonProperty("doi")
    public String getDoi() {
        return doi;
    }

    @JsonProperty("doi")
    public void setDoi(String doi) {
        this.doi = doi;
    }

    public LodData withDoi(String doi) {
        this.doi = doi;
        return this;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    public LodData withImage(String image) {
        this.image = image;
        return this;
    }

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public LodData withLinks(List<Link> links) {
        this.links = links;
        return this;
    }

    @JsonProperty("keywords")
    public List<String> getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public LodData withKeywords(List<String> keywords) {
        this.keywords = keywords;
        return this;
    }

    @JsonProperty("license")
    public String getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(String license) {
        this.license = license;
    }

    public LodData withLicense(String license) {
        this.license = license;
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

    public LodData withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("full_download")
    public List<FullDownload> getFullDownload() {
        return fullDownload;
    }

    @JsonProperty("full_download")
    public void setFullDownload(List<FullDownload> fullDownload) {
        this.fullDownload = fullDownload;
    }

    public LodData withFullDownload(List<FullDownload> fullDownload) {
        this.fullDownload = fullDownload;
        return this;
    }

    @JsonProperty("example")
    public List<Example> getExample() {
        return example;
    }

    @JsonProperty("example")
    public void setExample(List<Example> example) {
        this.example = example;
    }

    public LodData withExample(List<Example> example) {
        this.example = example;
        return this;
    }

    @JsonProperty("sparql")
    public List<Sparql> getSparql() {
        return sparql;
    }

    @JsonProperty("sparql")
    public void setSparql(List<Sparql> sparql) {
        this.sparql = sparql;
    }

    public LodData withSparql(List<Sparql> sparql) {
        this.sparql = sparql;
        return this;
    }

    @JsonProperty("other_download")
    public List<Object> getOtherDownload() {
        return otherDownload;
    }

    @JsonProperty("other_download")
    public void setOtherDownload(List<Object> otherDownload) {
        this.otherDownload = otherDownload;
    }

    public LodData withOtherDownload(List<Object> otherDownload) {
        this.otherDownload = otherDownload;
        return this;
    }

    @JsonProperty("description")
    public Description getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Description description) {
        this.description = description;
    }

    public LodData withDescription(Description description) {
        this.description = description;
        return this;
    }

    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LodData withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    @JsonProperty("website")
    public String getWebsite() {
        return website;
    }

    @JsonProperty("website")
    public void setWebsite(String website) {
        this.website = website;
    }

    public LodData withWebsite(String website) {
        this.website = website;
        return this;
    }

    @JsonProperty("triples")
    public Integer getTriples() {
        return triples;
    }

    @JsonProperty("triples")
    public void setTriples(Integer triples) {
        this.triples = triples;
    }

    public LodData withTriples(Integer triples) {
        this.triples = triples;
        return this;
    }

    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LodData withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public LodData withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    @JsonProperty("contact_point")
    public ContactPoint getContactPoint() {
        return contactPoint;
    }

    @JsonProperty("contact_point")
    public void setContactPoint(ContactPoint contactPoint) {
        this.contactPoint = contactPoint;
    }

    public LodData withContactPoint(ContactPoint contactPoint) {
        this.contactPoint = contactPoint;
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

    public LodData withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LodData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("identifier");
        sb.append('=');
        sb.append(((this.identifier == null)?"<null>":this.identifier));
        sb.append(',');
        sb.append("doi");
        sb.append('=');
        sb.append(((this.doi == null)?"<null>":this.doi));
        sb.append(',');
        sb.append("image");
        sb.append('=');
        sb.append(((this.image == null)?"<null>":this.image));
        sb.append(',');
        sb.append("links");
        sb.append('=');
        sb.append(((this.links == null)?"<null>":this.links));
        sb.append(',');
        sb.append("keywords");
        sb.append('=');
        sb.append(((this.keywords == null)?"<null>":this.keywords));
        sb.append(',');
        sb.append("license");
        sb.append('=');
        sb.append(((this.license == null)?"<null>":this.license));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("fullDownload");
        sb.append('=');
        sb.append(((this.fullDownload == null)?"<null>":this.fullDownload));
        sb.append(',');
        sb.append("example");
        sb.append('=');
        sb.append(((this.example == null)?"<null>":this.example));
        sb.append(',');
        sb.append("sparql");
        sb.append('=');
        sb.append(((this.sparql == null)?"<null>":this.sparql));
        sb.append(',');
        sb.append("otherDownload");
        sb.append('=');
        sb.append(((this.otherDownload == null)?"<null>":this.otherDownload));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("owner");
        sb.append('=');
        sb.append(((this.owner == null)?"<null>":this.owner));
        sb.append(',');
        sb.append("website");
        sb.append('=');
        sb.append(((this.website == null)?"<null>":this.website));
        sb.append(',');
        sb.append("triples");
        sb.append('=');
        sb.append(((this.triples == null)?"<null>":this.triples));
        sb.append(',');
        sb.append("domain");
        sb.append('=');
        sb.append(((this.domain == null)?"<null>":this.domain));
        sb.append(',');
        sb.append("namespace");
        sb.append('=');
        sb.append(((this.namespace == null)?"<null>":this.namespace));
        sb.append(',');
        sb.append("contactPoint");
        sb.append('=');
        sb.append(((this.contactPoint == null)?"<null>":this.contactPoint));
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
        result = ((result* 31)+((this.owner == null)? 0 :this.owner.hashCode()));
        result = ((result* 31)+((this.identifier == null)? 0 :this.identifier.hashCode()));
        result = ((result* 31)+((this.image == null)? 0 :this.image.hashCode()));
        result = ((result* 31)+((this.website == null)? 0 :this.website.hashCode()));
        result = ((result* 31)+((this.triples == null)? 0 :this.triples.hashCode()));
        result = ((result* 31)+((this.keywords == null)? 0 :this.keywords.hashCode()));
        result = ((result* 31)+((this.contactPoint == null)? 0 :this.contactPoint.hashCode()));
        result = ((result* 31)+((this.otherDownload == null)? 0 :this.otherDownload.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.fullDownload == null)? 0 :this.fullDownload.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.example == null)? 0 :this.example.hashCode()));
        result = ((result* 31)+((this.license == null)? 0 :this.license.hashCode()));
        result = ((result* 31)+((this.domain == null)? 0 :this.domain.hashCode()));
        result = ((result* 31)+((this.namespace == null)? 0 :this.namespace.hashCode()));
        result = ((result* 31)+((this.links == null)? 0 :this.links.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.sparql == null)? 0 :this.sparql.hashCode()));
        result = ((result* 31)+((this.doi == null)? 0 :this.doi.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LodData) == false) {
            return false;
        }
        LodData rhs = ((LodData) other);
        return (((((((((((((((((((((this.owner == rhs.owner)||((this.owner!= null)&&this.owner.equals(rhs.owner)))&&((this.identifier == rhs.identifier)||((this.identifier!= null)&&this.identifier.equals(rhs.identifier))))&&((this.image == rhs.image)||((this.image!= null)&&this.image.equals(rhs.image))))&&((this.website == rhs.website)||((this.website!= null)&&this.website.equals(rhs.website))))&&((this.triples == rhs.triples)||((this.triples!= null)&&this.triples.equals(rhs.triples))))&&((this.keywords == rhs.keywords)||((this.keywords!= null)&&this.keywords.equals(rhs.keywords))))&&((this.contactPoint == rhs.contactPoint)||((this.contactPoint!= null)&&this.contactPoint.equals(rhs.contactPoint))))&&((this.otherDownload == rhs.otherDownload)||((this.otherDownload!= null)&&this.otherDownload.equals(rhs.otherDownload))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.fullDownload == rhs.fullDownload)||((this.fullDownload!= null)&&this.fullDownload.equals(rhs.fullDownload))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.example == rhs.example)||((this.example!= null)&&this.example.equals(rhs.example))))&&((this.license == rhs.license)||((this.license!= null)&&this.license.equals(rhs.license))))&&((this.domain == rhs.domain)||((this.domain!= null)&&this.domain.equals(rhs.domain))))&&((this.namespace == rhs.namespace)||((this.namespace!= null)&&this.namespace.equals(rhs.namespace))))&&((this.links == rhs.links)||((this.links!= null)&&this.links.equals(rhs.links))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.sparql == rhs.sparql)||((this.sparql!= null)&&this.sparql.equals(rhs.sparql))))&&((this.doi == rhs.doi)||((this.doi!= null)&&this.doi.equals(rhs.doi))));
    }

}
