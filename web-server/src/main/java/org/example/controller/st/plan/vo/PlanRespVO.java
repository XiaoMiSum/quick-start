package org.example.controller.st.plan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.controller.st.review.vo.ReviewBaseVO;
import org.example.model.dto.Statistics;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanRespVO extends ReviewBaseVO {

    private Long id;

    private String memo;

    private Statistics statistics;

}
