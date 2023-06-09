package it.bitprojects.store.controller;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

@RestController
public class CtxController {

	@Autowired
	ApplicationContext context;

	@RequestMapping(value = "/handlersMapping")
	public String handleRequest() {

		Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
				HandlerMapping.class, true, false);

		matchingBeans.forEach((k, v) -> System.out.printf("order:%s %s=%s%n", ((Ordered) v).getOrder(), k,
				v.getClass().getSimpleName()));

		return "response from /test";

	}

}
