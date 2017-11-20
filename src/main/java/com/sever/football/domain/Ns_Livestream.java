package com.sever.football.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ns_Livestream.
 */
@Entity
@Table(name = "ns_livestream")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ns_Livestream implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "jhi_link")
    private String link;

    @NotNull
    @Column(name = "thumb_1", nullable = false)
    private String thumb1;

    @NotNull
    @Column(name = "thumb_2", nullable = false)
    private String thumb2;

    @Column(name = "status")
    private Boolean status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Ns_Livestream title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public Ns_Livestream link(String link) {
        this.link = link;
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumb1() {
        return thumb1;
    }

    public Ns_Livestream thumb1(String thumb1) {
        this.thumb1 = thumb1;
        return this;
    }

    public void setThumb1(String thumb1) {
        this.thumb1 = thumb1;
    }

    public String getThumb2() {
        return thumb2;
    }

    public Ns_Livestream thumb2(String thumb2) {
        this.thumb2 = thumb2;
        return this;
    }

    public void setThumb2(String thumb2) {
        this.thumb2 = thumb2;
    }

    public Boolean isStatus() {
        return status;
    }

    public Ns_Livestream status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
        Ns_Livestream ns_Livestream = (Ns_Livestream) o;
        if (ns_Livestream.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ns_Livestream.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ns_Livestream{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", link='" + getLink() + "'" +
            ", thumb1='" + getThumb1() + "'" +
            ", thumb2='" + getThumb2() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
