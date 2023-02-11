package com.example.springdemo.repository;

import com.example.springdemo.model.User;
import com.example.springdemo.validation.SqlInputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class DangerUserRepository implements CustomUserRepository {

    private static final String FIND_USER_BY_USERNAME =
            "select email, password, active " +
                    "from public.users u " +
                    "where u.username = '%s';";

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findUserByUsername(String username) {
        String formattedQuery = String.format(FIND_USER_BY_USERNAME, username);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        List<User> listOfUsers = new ArrayList<User>();
        if(result == null || result.size() == 0) {
            User user = new User();
            user.setMessage("USERNAME DOESN'T EXIST!");
            listOfUsers.add(user);
            return listOfUsers;
        }
        for(int i=0; i < result.size(); i++){
            System.out.println("Result is not null");
            Object[] userDB = result.get(i);
            User user = new User();
            user.setEmail(userDB[0].toString());
            System.out.println(userDB[0].toString());
            user.setPassword(userDB[1].toString());
            System.out.println(userDB[1].toString());
            user.setActive(userDB[2].toString());
            System.out.println(userDB[2].toString());
            listOfUsers.add(user);

        }

        return listOfUsers;
    }

    /*Optional<User> queryDatabase(String query, Object... params) {
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
            user.setMessage("USERNAME DOESN'T EXIST!");
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
    }*/

    /*@Override
    public List<User> findUserByUsername(String username) {
        return queryDatabase(FIND_USER_BY_USERNAME, username);
    }*/


    /*List<User> queryDatabase(String query, String username) {
        String formattedQuery = String.format(query, username);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
       /* StringBuffer s = new StringBuffer();
        StringBuffer b = new StringBuffer();
        StringBuffer d = new StringBuffer();
        String email="";
        String active="";
        String password="";*/
        /*List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            User user = new User();
            user.setMessage("USERNAME DOESN'T EXIST!");
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
    }*/

}
