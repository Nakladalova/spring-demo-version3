package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import com.example.springdemo.validation.SqlInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class SecureUserRepository implements CustomUserRepository {

    private static final String FIND_USER_BY_USERNAME =
            "select email, password, active " +
                    "from public.users u " +
                    "where u.username = '%s';";

    @Autowired
    private SqlInputValidator sqlInputValidator;

    @PersistenceContext
    private EntityManager em;

    /*@Override
    public Optional<User> findUserByUsername(String username) {
        boolean valid = sqlInputValidator.isValidUsername(username);
        if (!valid) {
            User user = new User();
            user.setMessage("NO DATA - INVALID USERNAME!");
            return Optional.of(user);
        }
        return queryDatabase(FIND_USER_BY_USERNAME, username);
    }*/

    @Override
    public List<User> findUserByUsername(String username) {
        return null;
    }


    Optional<User> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        StringBuffer s = new StringBuffer();
        StringBuffer b = new StringBuffer();
        StringBuffer d = new StringBuffer();
        String email = "";
        String active = "";
        String password = "";
        List<Object[]> result = nativeQuery.getResultList();
        if (result == null || result.size() == 0) {
            User user = new User();
            user.setMessage("USERNAME DOESN'T EXIST!");
            return Optional.of(user);
        }
        for (int i = 0; i < result.size(); i++) {
            Object[] userDB = result.get(i);
            email = userDB[0].toString();
            password = userDB[1].toString();
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

}

