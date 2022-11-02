package com.example.springdemo.repository;

import com.example.springdemo.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class DangerEmployeeRepository{

    private static final String FIND_EMPLOYEE_BY_ID =
            "select ename " +
                    "from public.employees e " +
                    "where e.id = %s";

    @PersistenceContext
    private EntityManager em;


    //@Override
    public Optional<Employee> findEmployeeById(String Id) {
        return queryDatabase(FIND_EMPLOYEE_BY_ID, Id);
    }

    private Optional<Employee> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if (result == null || result.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(mapResultToUser(result.get(0)));
    }

    private Employee mapResultToUser(Object[] result) {
        Employee employee = new Employee();
        employee.setId(new Integer(result[0].toString()));
        /*user.setUsername(result[1].toString());
        user.setPassword(result[2].toString());
        user.setName(result[3].toString());
        user.setSurname(result[4].toString());*/
        return employee;
    }



    //@Autowired
   /* private EntityManager entityManager;

    public List<Employee> findByID(String ID){
        var jpql="FROM Employee WHERE id='" + ID +"'";
        var query = entityManager.createQuery(jpql, Employee.class);
        return query.getResultList();
    }

    /*public List<Employee> unsafeJpaFindNameById(String ID) {
        String jql = "from Employees where id = '" + ID + "'";
        TypedQuery<Employees> q = em.createQuery(jql, Account.class);
        return q.getResultList()
                .stream()
                .map(this::toAccountDTO)
                .collect(Collectors.toList());
    }*/

    /*public List<Employee> findByID(String ID){
        String jql = "from Employee where name = '" + ID + "'";
        TypedQuery<Employee> q = em.createQuery(jql, Employee.class);
        return q.getResultList();
    }*/

}
