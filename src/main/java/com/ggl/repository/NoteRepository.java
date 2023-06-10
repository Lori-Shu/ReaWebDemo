package com.ggl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ggl.dto.NoteSelectPageDetail;
import com.ggl.entity.Note;

public interface NoteRepository extends JpaRepository<Note,String>{
    @Query(nativeQuery = true ,value =  "select * from t_note as t where t.user_id=?1 and t.title like %?2% limit 10 offset ?3")
    public List<Note>  selectPage( String userId,String partTitle,int startCount );
    @Query(nativeQuery = true,value="select count(id) from t_note as t where t.user_id=?1 and t.title like %?2%")
    public int countNotePage(String userId,String keyword);
}
