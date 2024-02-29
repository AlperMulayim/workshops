package com.alper.streamcars;

import com.alper.streamcars.employees.Employee;
import com.alper.streamcars.employees.EmployeeRepository;
import com.alper.streamcars.employees.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class StreamCarsApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StreamCarsApplication.class, args);
		EmployeeService service = context.getBean(EmployeeService.class);
//		List<Employee> employeeList = service.getEmployees();

		List<Employee> employeeList = service.filterByAge(30);
		for (int i = 0 ; i < employeeList.size(); ++i ) {
			System.out.println(i+1 + " - " + employeeList.get(i));
		}

		List<Employee> employeeList2 = service.filterByAgeAndSalary(45,25000);
		for (int i = 0 ; i < employeeList2.size(); ++i ) {
			System.out.println(i+1 + " - " + employeeList2.get(i));
		}
		List<Employee> emp3 = service.filterByAgeAndSalarySortByAgeEarning(45,25000);
		printList(emp3);

		printList(service.filterByNameStarts("Ad"));
		printList(service.filterByNameStarts("Be"));
		printList(service.filterByDepartmentAndCountry("HR","Cyprus"));
		printList(service.filterByDepartmentMakeUserNameUpperCaseAndSalary("IT"));
		System.out.println(service.findMaxSalary().get());
		System.out.println(service.findMinSalary().get());
		printList(service.createEmpDtos());
		System.out.println(service.findTotalPrice());
		service.optionalTrain();
		System.out.println(service.groupEmployees());

		service.printEmployees();
	}

	public static void printList(List list){
		for (int i = 0 ; i < list.size(); ++i ) {
			System.out.println(i+1 + " - " + list.get(i));
		}
	}

}
