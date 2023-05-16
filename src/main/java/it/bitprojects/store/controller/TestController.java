package it.bitprojects.store.controller;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class TestController {

	@Autowired
	ApplicationContext context;

	@RequestMapping(value = "/test")
	@ResponseBody
	public String handleRequest() {

		Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
				HandlerMapping.class, true, false);

		matchingBeans.forEach((k, v) -> System.out.printf("order:%s %s=%s%n", ((Ordered) v).getOrder(), k,
				v.getClass().getSimpleName()));
		
		return "response from /test";

	}

}
