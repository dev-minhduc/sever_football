package com.sever.football.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ns_Rank.
 */
@Entity
@Table(name = "ns_rank")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ns_Rank implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "battle")
    private Integer battle;

    @Column(name = "win")
    private Integer win;

    @Column(name = "draw")
    private Integer draw;

    @Column(name = "lose")
    private Integer lose;

    @Column(name = "diff")
    private String diff;

    @Column(name = "point")
    private Integer point;

    @Column(name = "satus")
    private Boolean satus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Ns_Rank name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Ns_Rank thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getBattle() {
        return battle;
    }

    public Ns_Rank battle(Integer battle) {
        this.battle = battle;
        return this;
    }

    public void setBattle(Integer battle) {
        this.battle = battle;
    }

    public Integer getWin() {
        return win;
    }

    public Ns_Rank win(Integer win) {
        this.win = win;
        return this;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getDraw() {
        return draw;
    }

    public Ns_Rank draw(Integer draw) {
        this.draw = draw;
        return this;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLose() {
        return lose;
    }

    public Ns_Rank lose(Integer lose) {
        this.lose = lose;
        return this;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public String getDiff() {
        return diff;
    }

    public Ns_Rank diff(String diff) {
        this.diff = diff;
        return this;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public Integer getPoint() {
        return point;
    }

    public Ns_Rank point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Boolean isSatus() {
        return satus;
    }

    public Ns_Rank satus(Boolean satus) {
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
        Ns_Rank ns_Rank = (Ns_Rank) o;
        if (ns_Rank.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ns_Rank.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ns_Rank{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", battle='" + getBattle() + "'" +
            ", win='" + getWin() + "'" +
            ", draw='" + getDraw() + "'" +
            ", lose='" + getLose() + "'" +
            ", diff='" + getDiff() + "'" +
            ", point='" + getPoint() + "'" +
            ", satus='" + isSatus() + "'" +
            "}";
    }
}
