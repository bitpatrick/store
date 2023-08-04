package it.bitprojects.store.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import it.bitprojects.store.entity.User;
import it.bitprojects.store.model.UserDetailsImpl;
import it.bitprojects.store.repository.UserRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private UserDetailsManager userDetailsManager;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() != null) { // Controlla se il contesto è quello di root
			return;
		}

		User user = new User();
		user.setActive(true);
		user.setUsername("supermario");
		user.setPassword("{noop}password");

		UserDetails userDetails = UserDetailsImpl.build(user);

		userDetailsManager.createUser(userDetails);

//        try {
//            // Leggi l'immagine da un file nel classpath
//            ClassPathResource imgFile = new ClassPathResource("images/office.jpg");
//            byte[] data = StreamUtils.copyToByteArray(imgFile.getInputStream());
//
//            // Creazione di SimpleJdbcInsert
//            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                    .withTableName("images")
//                    .usingColumns("name", "blob");
//
//            // Preparazione dei parametri
//            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("name", "office");
//            parameters.put("blob", new SqlLobValue(new ByteArrayInputStream(data), data.length));
//
//            // Esegui l'operazione di inserimento
//            simpleJdbcInsert.execute(parameters);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

	}
}
