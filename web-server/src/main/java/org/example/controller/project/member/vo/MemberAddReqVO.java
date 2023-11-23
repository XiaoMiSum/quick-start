package org.example.controller.project.member.vo;

import lombok.Data;

import java.util.List;

@Data
public class MemberAddReqVO {

    private Long projectId;

    private List<Long> userIds;
}
