package com.ggl.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.dto.AddNoteDetail;
import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.CommonResult;
import com.ggl.entity.Note;
import com.ggl.service.NoteService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/note")
public class NoteController {
    private NoteService noteService;
    public NoteController(NoteService n){
        noteService=n;
    }
    @PostMapping("/selectPage")
    public Mono<CommonResult<List<Note>>> selectPage(@RequestBody NoteSelectPageDetail detail){
        CompletableFuture<CommonResult<List<Note>>> res = noteService.selectPage(detail);
        return Mono.fromFuture(res);
    }
    @PostMapping("/addNote")
    public Mono<CommonResult<String>> addNote(@RequestBody AddNoteDetail detail){
        CompletableFuture<CommonResult<String>> res = noteService.addNote(detail);
        return Mono.fromFuture(res);
    }
}
