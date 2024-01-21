package kr.co.yoomin.myrestfulservice.repository;

import kr.co.yoomin.myrestfulservice.bean.Post;
import kr.co.yoomin.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository <Post, Integer>{
}
