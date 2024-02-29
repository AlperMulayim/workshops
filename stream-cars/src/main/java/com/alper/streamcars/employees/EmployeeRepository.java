package com.alper.streamcars.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee,Integer> {

    @Query(value = "SELECT e FROM Employee e WHERE e.age > :age")
    List<Employee> findMy(Integer age);

    @Query(value = "SELECT COUNT(e) FROM Employee e WHERE e.age > :age")
    Integer countMy(Integer age);


}
