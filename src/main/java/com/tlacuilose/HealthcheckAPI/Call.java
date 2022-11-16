package com.tlacuilose.HealthcheckAPI;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Call {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String uri;

    @ElementCollection
    private List<CallResponseField> reponseStructure;

    private Timestamp dateCreated;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "call")
    private Set<CallResponse> callResponses;

    @OneToMany(mappedBy = "call")
    private Set<CallReport> reports;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<CallResponseField> getReponseStructure() {
        return reponseStructure;
    }

    public void setReponseStructure(List<CallResponseField> reponseStructure) {
        this.reponseStructure = reponseStructure;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CallResponse> getCallResponses() {
        return callResponses;
    }

    public void setCallResponses(Set<CallResponse> callResponses) {
        this.callResponses = callResponses;
    }

    public Set<CallReport> getReports() {
        return reports;
    }

    public void setReports(Set<CallReport> reports) {
        this.reports = reports;
    }
}
