package com.projet.fip1.microsoft_store_back.entities;

public class RatingStatsDTO {
    private Long applicationId;
    private Double averageRating;
    private Long commentCount;

    public RatingStatsDTO() {}

    public RatingStatsDTO(Long applicationId, Double averageRating, Long commentCount) {
        this.applicationId = applicationId;
        this.averageRating = averageRating;
        this.commentCount = commentCount;
    }

    // Getters et setters
    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}