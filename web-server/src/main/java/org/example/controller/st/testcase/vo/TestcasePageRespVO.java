package org.example.controller.st.testcase.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestcasePageRespVO extends TestcaseBaseVO {

    private Long id;

    private String path;

    private String chargeUser;

    private String reviewed;

    private Date updateTime;
}
