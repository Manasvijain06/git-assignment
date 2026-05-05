package com.manasvi.reimbursement.dto.Request;

import jakarta.validation.constraints.Size;

public class ClaimActionRequest {
    @Size(max = 10000, message = "Comment must not exceed 10000 characters")
    private String comment;

    public ClaimActionRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}