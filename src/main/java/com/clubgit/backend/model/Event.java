package com.clubgit.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    /**
     * Commission organisatrice (peut être null pour un événement global ClubGIT).
     */
    @ManyToOne
    @JoinColumn(name = "commission_id")
    private Commission commission;

    /**
     * Points ajoutés en cas de présence.
     */
    private int pointsReward;

    /**
     * Points retirés en cas d'absence non justifiée.
     */
    private int pointsPenalty;

    public Event() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public int getPointsReward() {
        return pointsReward;
    }

    public void setPointsReward(int pointsReward) {
        this.pointsReward = pointsReward;
    }

    public int getPointsPenalty() {
        return pointsPenalty;
    }

    public void setPointsPenalty(int pointsPenalty) {
        this.pointsPenalty = pointsPenalty;
    }
}

