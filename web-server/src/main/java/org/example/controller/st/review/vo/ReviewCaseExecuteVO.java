package org.example.controller.st.review.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCaseExecuteVO {

    private Long id;

    private String result;

    private Long reviewer;
}