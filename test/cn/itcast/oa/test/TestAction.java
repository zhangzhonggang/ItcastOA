package cn.itcast.oa.test;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class TestAction extends ActionSupport{
	
	@Resource
	private TestService testService;
	
	@Override
	public String execute() throws Exception {
		System.out.println("----> TestAction.execute()");
//		testService.saveTwoUsers();
		return "success";
	}
}
