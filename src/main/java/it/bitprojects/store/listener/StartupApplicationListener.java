package it.bitprojects.store.listener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	
    	if (event.getApplicationContext().getParent() != null) { // Controlla se il contesto è quello di root
            return;
        }
    	
        try {
            // Leggi l'immagine da un file nel classpath
            ClassPathResource imgFile = new ClassPathResource("images/office.jpg");
            byte[] data = StreamUtils.copyToByteArray(imgFile.getInputStream());

            // Creazione di SimpleJdbcInsert
            SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("images")
                    .usingColumns("name", "blob");

            // Preparazione dei parametri
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", "office");
            parameters.put("blob", new SqlLobValue(new ByteArrayInputStream(data), data.length));

            // Esegui l'operazione di inserimento
            simpleJdbcInsert.execute(parameters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

