package com.ggl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.Note;

public interface NoteRepository extends JpaRepository<Note,String>{
    @Query("select n from Note n where n.userId=#{#detail.userId} and n.title like *#{#detail.title}* limit (#{#detail.targetPage}-1)*10+1,10")
    public List<Note>  selectPage(@Param("detail") NoteSelectPageDetail detail);
}