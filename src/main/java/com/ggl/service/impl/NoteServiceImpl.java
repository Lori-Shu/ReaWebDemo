package com.ggl.service.impl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggl.dto.AddNoteDetail;
import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.CommonResult;
import com.ggl.entity.Note;
import com.ggl.repository.NoteRepository;
import com.ggl.service.NoteService;

import io.netty.util.concurrent.CompleteFuture;
import lombok.extern.slf4j.Slf4j;
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
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
    public CompletableFuture <CommonResult<NoteSelectPageDetail>> selectPage(NoteSelectPageDetail detail) {
        int startCount=(detail.getTargetPage()-1)*10;
        log.info("传入的keyword："+detail.getKeyword());
        List<Note> res= noteRepository.selectPage(detail.getUserId(),detail.getKeyword(),startCount);
        log.info("note select res:"+res.toString());
        int ct = noteRepository.countNotePage(detail.getUserId(), detail.getKeyword());
        if(ct%10==0){
        detail.setAllPages(ct/10);
        }else if(ct%10!=0){
            detail.setAllPages(ct/10+1);
        }
        detail.setNotes(res);
        return CompletableFuture.completedFuture(CommonResult.success(detail));
    }
    /**
     * 新增note或修改已有note
     */
    @Override
    @Async
    public CompletableFuture<CommonResult< String>> addNote(AddNoteDetail detail) {
        Note newNote=new Note();
        newNote.setUserId(detail.getUserId());
        newNote.setTitle(detail.getTitle());
        newNote.setContent(detail.getContent());
        noteRepository.save(newNote);
        return CompletableFuture.completedFuture(CommonResult.success("新增note或修改已有note成功！"));
    }

    @Override
    @Async
    public CompletableFuture<CommonResult<String>> deleteNote(Note n) {
        noteRepository.delete(n);
        return CompletableFuture.completedFuture(CommonResult.success("删除Note成功"));
    }
    
}
