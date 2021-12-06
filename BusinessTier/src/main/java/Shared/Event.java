package Shared;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Event implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("address")
    private Address address;
    
    //private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public void setId(int id) {
        this.id = id;
    }
}
