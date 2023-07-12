package config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.creceperu.app.service.UsuarioService;

@Component
public class PopulateConfig {

	@Autowired UsuarioService usuarioService;

	@PostConstruct
	public void populateDatabase() {
		usuarioService.init();
	}
}
