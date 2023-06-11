package com.ggl.dto;

import lombok.Data;

@Data
public class AddNoteDetail {
    private String id;
    private String userId;
    private String title;
    private String content;
}
