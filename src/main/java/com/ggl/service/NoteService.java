package com.ggl.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.Note;



public interface NoteService {
    public CompletableFuture<List<Note>> selectPage(NoteSelectPageDetail detail);
}
