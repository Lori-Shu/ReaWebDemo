package com.ggl.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.ggl.dto.AddNoteDetail;
import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.CommonResult;
import com.ggl.entity.Note;



public interface NoteService {
    public CompletableFuture<CommonResult<NoteSelectPageDetail>> selectPage(NoteSelectPageDetail detail);
    
    public CompletableFuture<CommonResult <String>> addNote(AddNoteDetail detail);
    
    public CompletableFuture<CommonResult<String>> deleteNote(Note n);
}
