package io.github.xiaomisum.quickclick.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long userId;

    private String username;

    private LocalDateTime time;

    private String content;
}
