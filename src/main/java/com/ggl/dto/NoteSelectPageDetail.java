package com.ggl.dto;

import java.util.List;

import com.ggl.entity.Note;

import lombok.Data;

@Data
public class NoteSelectPageDetail {
    private String userId;
    private String keyword;
    private int targetPage;
    private int allPages;
    List<Note> notes;
}
