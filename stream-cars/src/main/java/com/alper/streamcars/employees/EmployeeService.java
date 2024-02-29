package com.alper.streamcars.employees;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class EmployeeService {
    @Autowired
    EmployeeRepository repository;

    public List<Employee> getEmployees(){
        return this.repository.findAll();
    }

    public List<Employee> filterByAge(int age){
        return  this.repository.findAll().stream()
                .filter(e->e.getAge() == age)
                .collect(Collectors.toList());
    }


    public List<Employee> filterByAgeAndSalary(int age, int salary){
        return this.repository.findAll().stream()
                .filter(e->e.getAge() == age && e.getSalary() < salary)
                .sorted(Comparator.comparingInt(Employee::getSalary))
                .collect(Collectors.toList());
    }

    public List<Employee> filterByAgeAndSalarySortByAgeEarning(int age, int salary){
        return repository.findAll().stream()
                .filter(e->e.getAge() < age && e.getSalary() < salary)
                .sorted(Comparator.comparingInt(e-> e.getSalary() / e.getAge()))
                .collect(Collectors.toList());
    }

    public List<Employee> filterByNameStarts(String x){
        return repository.findAll().stream()
                .filter(e-> e.getName().startsWith(x))
                .collect(Collectors.toList());
    }

    public List<Employee> filterByDepartmentAndCountry(String dep, String country){
        return repository.findAll().stream()
                .filter(e->e.getCountry().equals(country) && e.getDepartment().equals(dep))
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.toList());
    }

    public List<String> filterByDepartmentMakeUserNameUpperCaseAndSalary(String dep){
        return repository.findAll().stream()
                .filter(e->e.getDepartment().equals(dep))
                .map(e->  e.getName().toUpperCase(Locale.ROOT) + " " + e.getSurname().toUpperCase(Locale.ROOT) + " " + e.getSalary())
                .limit(10)
                .collect(Collectors.toList());
    }

    public Optional<Employee> findMaxSalary(){
        return repository.findAll().stream()
                .max(Comparator.comparingInt(Employee::getSalary));
    }

    public Optional<Employee> findMinSalary(){
        return repository.findAll().stream()
                .min(Comparator.comparingInt(Employee::getSalary));
    }

    public List<EmpDto> createEmpDtos(){
        return  repository.findAll().stream()
                .map(e-> new EmpDto(e.getName(),e.getCountry(),e.getSalary()))
                .collect(Collectors.toList());
    }

//  SELECT  SUM(E.salary) FROM employees  as E;
    public Integer findTotalPrice(){
        return repository.findAll().stream()
                .map(e->e.getSalary())
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void optionalTrain(){

        EmpDto dto  =  new EmpDto("Wat","Cyprus",1344);
        EmpDto dt = null;
        Optional<EmpDto> dtoOpt = Optional.of(dto);
        Optional<EmpDto> empOpt = Optional.empty();
        Optional<EmpDto> empNull = Optional.ofNullable(dt);

        System.out.println (empOpt +" -> " + empOpt.isEmpty());
        System.out.println(dtoOpt);
        System.out.println(dtoOpt + " ->  " + dtoOpt.isPresent() );
    }

    public Map<String,List<Employee>> groupEmployees(){
        return repository.findAll().stream()
                .limit(3)
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public void printEmployees(){
        List<Employee> employees = repository.findMy(60);
        System.out.println("my custom query  ->   ");
        employees.stream().forEach(e->System.out.println(e));

        Integer age = 63;
        Integer countMy = repository.countMy(age);
        System.out.println("TOTAL : " + countMy + " emplyees over " + age + " years old.");


    }


}
