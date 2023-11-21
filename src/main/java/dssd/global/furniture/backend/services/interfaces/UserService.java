package dssd.global.furniture.backend.services.interfaces;

import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Rol;

@Service
public interface UserService {
	public boolean login(String username,String password);
	public Rol getRole(String username);
}
