package yu.cs.spring.model.master.input;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.Employee.Gender;

public record EmployeeFormForUpdate(
		@NotBlank(message = "Please enter employee name.")
		String name,
		@NotBlank(message = "Please enter phone.")
		String phone,
		@NotBlank(message = "Please enter email.")
		String email,
		@NotNull(message = "Please select gender.")
		Gender gender,
		@NotNull(message = "Please enter date of birth.")
		LocalDate dob,
		@NotNull(message = "Please enter assign date.")
		LocalDate assignDate,
		String remark) {

	public static EmployeeFormForUpdate from(Employee entity) {
		return new EmployeeFormForUpdate(
				entity.getAccount().getName(), 
				entity.getPhone(), 
				entity.getEmail(), 
				entity.getGender(), 
				entity.getDateOfBirth(), 
				entity.getAssignDate(), 
				entity.getRemark());
	}
}
