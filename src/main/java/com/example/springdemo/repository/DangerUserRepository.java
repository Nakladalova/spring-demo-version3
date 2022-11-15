package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class DangerUserRepository implements CustomUserRepository {

    private static final String FIND_USER_BY_USERNAME =
            "select email, password, active " +
                    "from public.users u " +
                    "where u.username = '%s';";

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<User> findUserByUsername(String username) {
        return queryDatabase(FIND_USER_BY_USERNAME, username);
    }


    Optional<User> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        StringBuffer s = new StringBuffer();
        StringBuffer b = new StringBuffer();
        StringBuffer d = new StringBuffer();
        String email="";
        String active="";
        String password="";
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            User user = new User();
            user.setEmail("-");
            return Optional.of(user);
        }
        for(int i=0; i < result.size(); i++){
            Object[] userDB = result.get(i);
            email = userDB[0].toString();
            password =  userDB[1].toString();
            active = userDB[2].toString();
            s.append(email + " ");
            b.append(password + "  ");
            d.append(active + " ");
        }
        String nameFromDatabase = s.toString();
        String passwordFromDatabase = b.toString();
        String activeFromDatabase = d.toString();
        User user = new User();
        user.setEmail(nameFromDatabase);
        user.setPassword(passwordFromDatabase);
        user.setActive(activeFromDatabase);
        return Optional.of(user);
    }


     /*Optional<User> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(mapResultToUser(result.get(0)));
    }

    private User mapResultToUser( Object[] result) {
        User user = new User();
        user.setId(Long.valueOf(result[0].toString()));
        user.setEmail(result[1].toString());
        user.setUsername(result[2].toString());
        user.setActive(result[3].toString());
        return user;
    }*/


    /*Optional<User> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        StringBuffer s = new StringBuffer();
        String name="";
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            User user = new User();
            user.setUsername("No Employee with this ID");
            return Optional.of(user);
        }
        for(int i=0; i < result.size(); i++){
            Object[] userDB = result.get(i);
            name = userDB[0].toString();
            s.append(name + " ");
        }
        String nameFromDatabase = s.toString();
        User user = new User();
        user.setEmail(nameFromDatabase);
        return Optional.of(user);
    }*/

}
