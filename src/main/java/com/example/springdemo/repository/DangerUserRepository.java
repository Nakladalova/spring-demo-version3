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
            "select id, username " +
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
    }

}
