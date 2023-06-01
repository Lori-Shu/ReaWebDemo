package com.ggl.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.Note;
import com.ggl.repository.NoteRepository;
import com.ggl.service.NoteService;

import io.netty.util.concurrent.CompleteFuture;
@Service
public class NoteServiceImpl implements NoteService{

    
    private NoteRepository noteRepository;
    public NoteServiceImpl(NoteRepository r){
        noteRepository=r;
    }
    
    /**
     * 分页按标题模糊查询
     */
    @Override
    @Async
    public CompletableFuture <List<Note>> selectPage(NoteSelectPageDetail detail) {
        List<Note> res= noteRepository.selectPage(detail);
        return CompletableFuture.completedFuture(res);
    }
    
}
