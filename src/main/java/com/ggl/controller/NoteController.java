package com.ggl.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.dto.AddNoteDetail;
import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.CommonResult;
import com.ggl.entity.Note;
import com.ggl.service.NoteService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController {
    private NoteService noteService;
    private ObjectMapper objectMapper;
    public NoteController(NoteService n,ObjectMapper m){
        noteService=n;
        objectMapper=m;
    }
    @PostMapping("/selectPage")
    public Mono<CommonResult<NoteSelectPageDetail>> selectPage(@RequestBody NoteSelectPageDetail detail){
        CompletableFuture<CommonResult<NoteSelectPageDetail>> res = noteService.selectPage(detail);
        return Mono.fromFuture(res);
    }
    /*
     * 新增或修改note
     */
    @PostMapping("/addNote")
    public Mono<CommonResult<String>> addNote(@RequestBody AddNoteDetail detail) throws JsonProcessingException{
        CompletableFuture<CommonResult<String>> res = noteService.addNote(detail);
        return Mono.fromFuture(res);
    }
    
    @PostMapping("/delete")
    public Mono<CommonResult<String>> deleteNote(@RequestBody Note n) {
        CompletableFuture<CommonResult<String>> res = noteService.deleteNote(n);
        return Mono.fromFuture(res);
    }
}
