package com.clubgit.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quest_submissions")
public class QuestSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private User member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestSubmissionStatus status = QuestSubmissionStatus.PENDING;

    private LocalDateTime submittedAt;

    private LocalDateTime validatedAt;

    /**
     * Chef de commission ou membre du Bureau qui a validé / refusé.
     */
    @ManyToOne
    @JoinColumn(name = "validator_id")
    private User validator;

    @Column(length = 2000)
    private String comment;

    @PrePersist
    protected void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }

    public QuestSubmission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public QuestSubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(QuestSubmissionStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getValidatedAt() {
        return validatedAt;
    }

    public void setValidatedAt(LocalDateTime validatedAt) {
        this.validatedAt = validatedAt;
    }

    public User getValidator() {
        return validator;
    }

    public void setValidator(User validator) {
        this.validator = validator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

