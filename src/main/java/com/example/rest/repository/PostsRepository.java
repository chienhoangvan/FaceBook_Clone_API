package com.example.rest.repository;

import com.example.rest.model.entity.Post;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository(value = "PostsDao")
public class PostsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Post> findPostByAll(String lastId, String count, String index) {
        List<Post> posts = null;
        if(StringUtils.isEmpty(lastId)){
            int countInt = Integer.parseInt(count);
            int indexInt = Integer.parseInt(index);
            String sql = "SELECT * FROM post WHERE post.deleted = false ORDER BY post.id DESC LIMIT :limit OFFSET :index";
            Query query = entityManager.createNativeQuery(sql, Post.class);
            posts = query.setParameter("limit", countInt)
                    .setParameter("index", indexInt)
                    .getResultList();
        }else{
            int countInt = Integer.parseInt(count);
            int lastIdInt = Integer.parseInt(lastId);
            int indexInt = Integer.parseInt(index);
            String sql = "SELECT * FROM post WHERE post.id> :last_id AND post.deleted = false ORDER BY post.id DESC LIMIT :limit OFFSET :index";
            Query query = entityManager.createNativeQuery(sql, Post.class);
            posts = query.setParameter("limit", countInt)
                    .setParameter("last_id", lastIdInt)
                    .setParameter("index", indexInt)
                    .getResultList();
        }

        return posts;
    }
}
