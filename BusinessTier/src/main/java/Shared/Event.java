package Shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import java.io.Serializable;

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

    public Event(){}

    public void setTitle(String title) throws IllegalArgumentException
    {
        if(title == null || title.isBlank() || title.isEmpty()) throw new IllegalArgumentException("Event title cannot be null");
        if(title.length() <= 2) throw new IllegalArgumentException("Event title cannot have less than 3 characters.");
        if (title.length() > 16) throw new IllegalArgumentException("Event title cannot have more than 16 characters.");
        this.title = title;
    }
    public void setAddress(Address address) throws Exception
    {
        address.runChecks();
        this.address = address;
    }
    public void setDescription(String description) throws IllegalArgumentException {
        if(description == null || description.isBlank() || description.isEmpty()) throw new IllegalArgumentException("Event description cannot be null");
        if(description.length() <= 2) throw new IllegalArgumentException("Event description cannot have less than 3 characters.");
        if (description.length() > 64) throw new IllegalArgumentException("Event description cannot have more than 64 characters.");
        this.description = description;
    }
    public void setEndTime(String endTime) throws IllegalArgumentException {
        if(endTime == null || endTime.isBlank() || endTime.isEmpty()) throw new IllegalArgumentException("Event start time cannot be null");
        this.endTime = endTime;
    }
    public void setStartTime(String startTime) throws IllegalArgumentException {
        if(startTime == null || startTime.isBlank() || startTime.isEmpty()) throw new IllegalArgumentException("Event end time cannot be null");
        this.startTime = startTime;
    }
    public void setId(int id) throws IllegalArgumentException {
        if(id < 0) throw new IllegalArgumentException("Event ID cannot be less than 0!");
        this.id = id;
    }
    
    public void runChecks() throws Exception
    {
        setId(this.id);
        setTitle(this.title);
        setDescription(this.description);
        setStartTime(this.startTime);
        setEndTime(this.endTime);
        setAddress(this.address);
    }
}
