package com.ggl.dto;

import lombok.Data;

@Data
public class AddNoteDetail {
    private String userId;
    private String title;
    private String content;
}
