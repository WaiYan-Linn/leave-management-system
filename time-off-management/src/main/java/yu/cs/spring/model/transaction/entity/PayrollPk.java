package yu.cs.spring.model.transaction.entity;

import java.io.Serializable;
import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PayrollPk implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "pay_month")
	private YearMonth payMonth;
	
	@Column(name = "employee_code")
	private String employeeCode;
}