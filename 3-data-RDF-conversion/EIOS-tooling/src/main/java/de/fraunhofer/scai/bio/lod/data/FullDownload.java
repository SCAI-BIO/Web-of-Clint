
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
    "media_type",
    "description",
    "status",
    "title",
    "mirror",
    "download_url"
})
@Generated("jsonschema2pojo")
public class FullDownload {

    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("description")
    private String description;
    @JsonProperty("status")
    private String status;
    @JsonProperty("title")
    private String title;
    @JsonProperty("mirror")
    private List<Object> mirror = new ArrayList<Object>();
    @JsonProperty("download_url")
    private String downloadUrl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("media_type")
    public String getMediaType() {
        return mediaType;
    }

    @JsonProperty("media_type")
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public FullDownload withMediaType(String mediaType) {
        this.mediaType = mediaType;
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

    public FullDownload withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    public FullDownload withStatus(String status) {
        this.status = status;
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

    public FullDownload withTitle(String title) {
        this.title = title;
        return this;
    }

    @JsonProperty("mirror")
    public List<Object> getMirror() {
        return mirror;
    }

    @JsonProperty("mirror")
    public void setMirror(List<Object> mirror) {
        this.mirror = mirror;
    }

    public FullDownload withMirror(List<Object> mirror) {
        this.mirror = mirror;
        return this;
    }

    @JsonProperty("download_url")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    @JsonProperty("download_url")
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public FullDownload withDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
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

    public FullDownload withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(FullDownload.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("mediaType");
        sb.append('=');
        sb.append(((this.mediaType == null)?"<null>":this.mediaType));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.status == null)?"<null>":this.status));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("mirror");
        sb.append('=');
        sb.append(((this.mirror == null)?"<null>":this.mirror));
        sb.append(',');
        sb.append("downloadUrl");
        sb.append('=');
        sb.append(((this.downloadUrl == null)?"<null>":this.downloadUrl));
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
        result = ((result* 31)+((this.mirror == null)? 0 :this.mirror.hashCode()));
        result = ((result* 31)+((this.downloadUrl == null)? 0 :this.downloadUrl.hashCode()));
        result = ((result* 31)+((this.description == null)? 0 :this.description.hashCode()));
        result = ((result* 31)+((this.mediaType == null)? 0 :this.mediaType.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FullDownload) == false) {
            return false;
        }
        FullDownload rhs = ((FullDownload) other);
        return ((((((((this.mirror == rhs.mirror)||((this.mirror!= null)&&this.mirror.equals(rhs.mirror)))&&((this.downloadUrl == rhs.downloadUrl)||((this.downloadUrl!= null)&&this.downloadUrl.equals(rhs.downloadUrl))))&&((this.description == rhs.description)||((this.description!= null)&&this.description.equals(rhs.description))))&&((this.mediaType == rhs.mediaType)||((this.mediaType!= null)&&this.mediaType.equals(rhs.mediaType))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))))&&((this.status == rhs.status)||((this.status!= null)&&this.status.equals(rhs.status))));
    }

}
