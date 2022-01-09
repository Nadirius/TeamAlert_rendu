package ch.hevs.ig.android.zemrani.teamalers.remaked.models;


import com.google.type.Date;

public class Alert {
    private String id = "";
    private String owner = "";
    private String teamOwner = "";
    private String name = "";
    private String subject = "";
    private int level = 0;
    private String adresse = "";
    private String description = "";
    private String img = "";
    private Date creation_date = null;
    private Date start_date = null;
    private Date end_date = null;
    
    public Alert() {
    }
    
    public String getTeamOwner() {
        return teamOwner;
    }
    
    public String getImg() {
        return img;
    }
    
    public String getId() {
        return id;
    }
    
    public String getOwner() {
        return owner;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Date getCreation_date() {
        return creation_date;
    }
    
    public Date getStart_date() {
        return start_date;
    }
    
    public Date getEnd_date() {
        return end_date;
    }
}
