package ch.hevs.ig.android.zemrani.teamalers.remaked.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String id = "";
    private String ownerId = "";
    private String ownerName = "";
    private String name = "";
    private String city = "";
    private List<String> members = new ArrayList<>();
    private String description;
    private String img;
    private Boolean ispublic;
    
    public List< String > getMembers() {
        return members;
    }
    
    public Team() {
    }
    
    public void setId( String id ) {
        this.id = id;
    }
    
    public void setOwner( String owner ) {
        this.ownerId = owner;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public void setCity( String city ) {
        this.city = city;
    }
    
    public void setDescription( String description ) {
        this.description = description;
    }
    
    public void setImg( String img ) {
        this.img = img;
    }
    
    public void setIspublic( Boolean ispublic ) {
        this.ispublic = ispublic;
    }
    
    public String getId() {
        return id;
    }
    
    public String getOwner() {
        return ownerId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getImg() {
        return img;
    }
    
    public Boolean getIspublic() {
        return ispublic;
    }
}
