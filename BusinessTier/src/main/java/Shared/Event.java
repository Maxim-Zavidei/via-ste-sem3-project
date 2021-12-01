package Shared;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Event implements Serializable {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String Title;

    @JsonProperty("description")
    private String Description;

    @JsonProperty("startTime")
    private LocalDateTime StartTime;

    @JsonProperty("endTime")
    private LocalDateTime EndTime;

    @JsonProperty("address")
    private Address Address;
}
