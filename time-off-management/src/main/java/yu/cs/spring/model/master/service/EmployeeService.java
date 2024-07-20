package yu.cs.spring.model.master.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import yu.cs.spring.model.master.EmployeeCodeGenerator;
import yu.cs.spring.model.master.entity.Department;
import yu.cs.spring.model.master.entity.Employee;
import yu.cs.spring.model.master.entity.EmployeeCodeSeq;
import yu.cs.spring.model.master.entity.Position;
import yu.cs.spring.model.master.entity.PositionPk;
import yu.cs.spring.model.master.input.EmployeeFormForCreate;
import yu.cs.spring.model.master.input.EmployeeFormForUpdate;
import yu.cs.spring.model.master.repo.DepartmentRepo;
import yu.cs.spring.model.master.repo.EmployeeCodeSeqRepo;
import yu.cs.spring.model.master.repo.EmployeeRepo;
import yu.cs.spring.model.master.repo.PositionRepo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private EmployeeCodeGenerator codeGenerator;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private DepartmentRepo departmentRepo;

	@Autowired
	private PositionRepo positionRepo;

	@Autowired
	private EmployeeCodeSeqRepo employeeCodeSeqRepository;

	public void saveEmployee(EmployeeFormForCreate form) {

		String departmentCode = form.department();
		
		System.out.println(employeeCodeSeqRepository.findByDepartment(departmentCode));

		// Fetch or create the sequence for the department
		EmployeeCodeSeq seq = employeeCodeSeqRepository.findByDepartment(departmentCode).orElseGet(() -> {
			EmployeeCodeSeq newSeq = new EmployeeCodeSeq();
			newSeq.setDepartment(departmentCode);
			newSeq.setSeqNumber(0); // Start from 0, it will be incremented in the next() method
			return newSeq;
		});

		// Generate the next code
		String code = seq.next();

		var password = encoder.encode("123456");

		var entity = form.entity(code, password);
		Optional<Department> department = departmentRepo.findById(form.department());

		PositionPk positionPk = new PositionPk();
		positionPk.setDepartmentCode(form.department());
		PositionPk.PositionCode positionCode = PositionPk.fromString(form.positionCode());

		positionPk.setPositionCode(positionCode);
		Optional<Position> position = positionRepo.findById(positionPk);

		entity.setDepartment(department.get());
		entity.setPosition(position.get());
		employeeRepo.saveAndFlush(entity);
	}

	public List<Employee> findAll() {
		return employeeRepo.findAll();
	}

	public Employee getEmployeeByUsername(String username) {
		return employeeRepo.findByAccountUsername(username);
	}

	public void deleteById(String id) {
		employeeRepo.deleteById(id);
	}

	public Employee findById(String id) {
		return employeeRepo.findByCode(id);
	}

	public void updateEmployee(String id, @Valid EmployeeFormForUpdate employeeForm) {
		Employee existingEmployee = employeeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		existingEmployee.getAccount().setName(employeeForm.name());
		existingEmployee.setPhone(employeeForm.phone());
		existingEmployee.setStatus(employeeForm.status());
		existingEmployee.setRemark(employeeForm.remark());

		employeeRepo.save(existingEmployee);
	}

}
