package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import yu.cs.spring.model.master.CalculationType;
import yu.cs.spring.model.master.entity.Deduction;

@Data
public abstract class DeductionForm {
	
	@NotBlank(message = "Please enter allowance name.")
	private String name;
	@NotNull(message = "Please select allowance type.")
	private CalculationType type;
	private String description;
	
	public abstract Deduction getEntity();

}
