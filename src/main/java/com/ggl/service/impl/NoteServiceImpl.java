package com.ggl.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.dto.AddNoteDetail;
import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.Note;
import com.ggl.repository.NoteRepository;
import com.ggl.service.NoteService;

import io.netty.util.concurrent.CompleteFuture;
@Service
@Transactional(rollbackFor = Exception.class)
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
        int startCount=(detail.getTargetPage()-1)*10;
        List<Note> res= noteRepository.selectPage(detail.getUserId(),detail.getPartTitle(),startCount);
        return CompletableFuture.completedFuture(res);
    }
    /**
     * 新增note
     */
    @Override
    @Async
    public CompletableFuture<String> addNote(AddNoteDetail detail) {
        Note newNote=new Note();
        newNote.setUserId(detail.getUserId());
        newNote.setTitle(detail.getTitle());
        newNote.setContent(detail.getContent());
        noteRepository.save(newNote);
        return CompletableFuture.completedFuture("新增note成功！");
    }
    
}
