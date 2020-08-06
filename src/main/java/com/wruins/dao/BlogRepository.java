package com.wruins.dao;

import com.wruins.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

        @Query("select b from Blog b where b.recommend = true")
        List<Blog> findTop(Pageable epageable);

        @Query("select b from Blog b where  b.content like ?1 or b.title like ?1")
        Page<Blog> findByQuery(String query,Pageable pageable);
}
