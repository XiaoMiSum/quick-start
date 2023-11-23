package org.example.controller.st.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewAddReqVO extends ReviewBaseVO {

    private String memo;
}
