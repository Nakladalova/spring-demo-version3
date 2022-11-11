package com.example.springdemo.repository;

import com.example.springdemo.model.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class DangerEmployeeRepository implements CustomEmployeeRepository {

    private static final String FIND_EMPLOYEE_BY_ID =
            "select ename, username " +
                    "from public.employees e " +
                    "where e.ename = '%s';";

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Employee> findEmployeeById(String id) {
        return queryDatabase(FIND_EMPLOYEE_BY_ID, id);
    }

     /* Optional<Employee> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(mapResultToUser(result.get(0)));
    }

    private Employee mapResultToUser( Object[] result) {
        Employee employee = new Employee();
        employee.setEname(result[0].toString());
        return employee;
    }

    ///*/

    Optional<Employee> queryDatabase(String query, Object... params) {
        String formattedQuery = String.format(query, params);
        Query nativeQuery = this.em.createNativeQuery(formattedQuery);
        StringBuffer s = new StringBuffer();
        String name="";
        List<Object[]> result = nativeQuery.getResultList();
        if(result == null || result.size() == 0) {
            Employee employee = new Employee();
            employee.setEname("No Employee with this ID");
            return Optional.of(employee);
        }
        for(int i=0; i < result.size(); i++){
            Object[] employeeDB = result.get(i);
            name = employeeDB[0].toString();
            s.append(name + " ");
        }
        String nameFromDatabase = s.toString();
        Employee employee = new Employee();
        employee.setEname(nameFromDatabase);
        return Optional.of(employee);
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
