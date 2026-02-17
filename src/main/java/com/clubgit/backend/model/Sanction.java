package com.clubgit.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanctions")
public class Sanction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private User member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "issued_by_id")
    private User issuedBy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SanctionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SanctionSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SanctionStatus status = SanctionStatus.ACTIVE;

    @Column(length = 2000, nullable = false)
    private String reason;

    /**
     * Variation de points apportée par la sanction (souvent négative).
     */
    private int pointsDelta;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Sanction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public User getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }

    public SanctionType getType() {
        return type;
    }

    public void setType(SanctionType type) {
        this.type = type;
    }

    public SanctionSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(SanctionSeverity severity) {
        this.severity = severity;
    }

    public SanctionStatus getStatus() {
        return status;
    }

    public void setStatus(SanctionStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getPointsDelta() {
        return pointsDelta;
    }

    public void setPointsDelta(int pointsDelta) {
        this.pointsDelta = pointsDelta;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

