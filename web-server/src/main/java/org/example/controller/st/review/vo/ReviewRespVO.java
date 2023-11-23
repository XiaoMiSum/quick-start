package org.example.controller.st.review.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewRespVO extends ReviewBaseVO {

    private Long id;

    private String memo;

}