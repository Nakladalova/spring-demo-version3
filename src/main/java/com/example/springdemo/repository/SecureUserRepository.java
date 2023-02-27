package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import com.example.springdemo.validation.SqlInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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

    @Override
    public List<User> findUserByUsername(String username) {
        List<User> listOfUsers = new ArrayList<User>();
        boolean valid = sqlInputValidator.isValidUsername(username);
        if (!valid) {
            User user = new User();
            user.setMessage("NO DATA - INVALID USERNAME!");
            listOfUsers.add(user);
            return listOfUsers;
        }
        String formattedQuery = String.format(FIND_USER_BY_USERNAME, username);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            User user = new User();
            user.setMessage("USERNAME DOESN'T EXIST!");
            listOfUsers.add(user);
            return listOfUsers;
        }
        for(int i=0; i < result.size(); i++){
            Object[] userDB = result.get(i);
            User user = new User();
            user.setEmail(userDB[0].toString());
            user.setPassword(userDB[1].toString());
            user.setActive(userDB[2].toString());
            listOfUsers.add(user);
        }
        return listOfUsers;
    }
}

