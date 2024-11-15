package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Comment;
import org.example.entities.Post;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String puName = "PU-NAME";
        Map<String,String> props = new HashMap<>();
        props.put("hibernate.show_sql","true");
        props.put("hibernate.hbm2ddl.auto","create");
        EntityManagerFactory emf = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName),props);
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Post post = new Post();
            post.setTitle("Post 1");
            post.setContent("Post Of Content 1");

            Comment c = new Comment();
            c.setContent("Content 1");
            c.setPost(post);
            Comment c2 = new Comment();
            c2.setContent("Content 2");
            c2.setPost(post);
            post.setComments(List.of(c,c2));

            em.persist(post);


            //em.remove(em.find(Post.class,1));





            em.getTransaction().commit();
        }finally {
            em.close();
        }
    }
}