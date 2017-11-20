package com.sever.football.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ns_Match_Schedule.
 */
@Entity
@Table(name = "ns_match_schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ns_Match_Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_1")
    private String team1;

    @Column(name = "team_2")
    private String team2;

    @Column(name = "thumbnail_1")
    private String thumbnail1;

    @Column(name = "thumbnail_2")
    private String thumbnail2;

    @Column(name = "satus")
    private Boolean satus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public Ns_Match_Schedule team1(String team1) {
        this.team1 = team1;
        return this;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Ns_Match_Schedule team2(String team2) {
        this.team2 = team2;
        return this;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public Ns_Match_Schedule thumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
        return this;
    }

    public void setThumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public Ns_Match_Schedule thumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
        return this;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public Boolean isSatus() {
        return satus;
    }

    public Ns_Match_Schedule satus(Boolean satus) {
        this.satus = satus;
        return this;
    }

    public void setSatus(Boolean satus) {
        this.satus = satus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ns_Match_Schedule ns_Match_Schedule = (Ns_Match_Schedule) o;
        if (ns_Match_Schedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ns_Match_Schedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ns_Match_Schedule{" +
            "id=" + getId() +
            ", team1='" + getTeam1() + "'" +
            ", team2='" + getTeam2() + "'" +
            ", thumbnail1='" + getThumbnail1() + "'" +
            ", thumbnail2='" + getThumbnail2() + "'" +
            ", satus='" + isSatus() + "'" +
            "}";
    }
}
