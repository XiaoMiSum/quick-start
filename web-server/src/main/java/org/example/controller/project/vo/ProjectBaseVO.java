package org.example.controller.project.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProjectBaseVO {

    private String name;

    private List<String> productManagers;

    private List<String> developers;

    private List<String> testers;

    private String memo;
}
