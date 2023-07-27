package it.bitprojects.store.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import it.bitprojects.store.model.Cart;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "it.bitprojects.store.controller")
public class MyWebConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/views/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD").allowCredentials(true);
	}

	/**
	 * LOGIN PAGE
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		ViewControllerRegistration r = registry.addViewController("/login");
		r.setViewName("login");
		r.setStatusCode(HttpStatus.OK);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/js/**").addResourceLocations("/static/js/");
		registry.addResourceHandler("/static/img/**").addResourceLocations("/static/img/");
		registry.addResourceHandler("/static/views/jsp/**").addResourceLocations("/static/views/jsp");
	}

	/**
	 * metodo aggiunto dall'academy per stampare pippo se intercettato nelle request
	 * 
	 * @return
	 */

	@Bean
	@Scope(WebApplicationContext.SCOPE_SESSION)
	public Cart cart() {
		return new Cart();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PippoInterceptorHandler());
		registry.addInterceptor(new IPLoggingInterceptorHandler());

		// HandlerInterceptor
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("localeCode");
		registry.addInterceptor(localeChangeInterceptor);

		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
//	@Bean
//    public LocaleResolver localeResolver () {
//        CookieLocaleResolver r = new CookieLocaleResolver();
//        r.setDefaultLocale(Locale.US);
//        r.setCookieName("localeInfo");
//
//        //if set to -1, the cookie is deleted
//        // when browser shuts down
//        r.setCookieMaxAge(24*60*60);
//        return r;
//    }

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
		return messageSource;
	}

//	@Bean
//	public JsonMapper jsonMapper() {
//		return new JsonMapper();
//	}

}