package com.ggl.dto;

import lombok.Data;

@Data
public class NoteSelectPageDetail {
    private String userId;
    private String partTitle;
    private int targetPage;
    private int allPages;
}
