package yu.cs.spring.model.master.input;

import jakarta.validation.constraints.NotBlank;

public record DepartmentFormForManagerChanges(
		@NotBlank(message = "Please select head of department code.")
		String headCode) {

}
