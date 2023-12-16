package com.github.xiaomisum.mstar.controller.track.plan.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlanAddReqVO extends PlanBaseVO {

    private String memo;
}
