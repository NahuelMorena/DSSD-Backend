package dssd.global.furniture.backend.services.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
	public boolean login(String username,String password);
	public String getRole(String username);
}
