package yu.cs.spring.controller;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import yu.cs.spring.model.master.service.AccountService;
import yu.cs.spring.model.master.service.LeaveApplicationService;
import yu.cs.spring.model.transaction.output.LeaveRequest;

@Controller
public class LoginController {
	
	@Autowired
	private LeaveApplicationService leaveApplicationService;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/")
	String start() {
		return "login";
	}
	
		
	@GetMapping("/home")
	String home(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = accountService.getNameByUserName(auth.getName());
		
		model.addAttribute("name", name);
        model.addAttribute("email", auth.getName());
		
		Function<String, Boolean> hasAuthority = authority -> 
			SecurityContextHolder.getContext().getAuthentication().
			getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
			
			 List<LeaveRequest> leaveApplications = leaveApplicationService.getAllLeaveApplications().stream()
		                .map(leaveApplication -> new LeaveRequest(
		                        leaveApplication.getId(),
		                        leaveApplication.getApplyAt(),
		                        leaveApplication.getEmployee().getAccount().getName(),
		                        leaveApplication.getType().getName(),
		                        leaveApplication.getStartDate(),
		                        leaveApplication.getEndDate(),
		                        leaveApplication.getRemark(),
		                        leaveApplication.getStatus()
		                ))
		                .collect(Collectors.toList());
		        model.addAttribute("leaveApplications", leaveApplications);
			
		
		if(hasAuthority.apply("Employee")) {
			List<LeaveRequest> userLeaves = leaveApplicationService.getLeaveApplicationsByUsername(auth.getName()).stream()
	                .map(leaveApplication -> new LeaveRequest(
	                        leaveApplication.getId(),
	                        leaveApplication.getApplyAt(),
	                        leaveApplication.getEmployee().getAccount().getName(),
	                        leaveApplication.getType().getName(),
	                        leaveApplication.getStartDate(),
	                        leaveApplication.getEndDate(),
	                        leaveApplication.getRemark(),
	                        leaveApplication.getStatus()
	                ))
	                .collect(Collectors.toList());
	       
	        model.addAttribute("leaveApplications", userLeaves);
			return "leave-request";
		}
		
		
		return "admin-home";
		
	}
	
}