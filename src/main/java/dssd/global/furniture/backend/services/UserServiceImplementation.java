package dssd.global.furniture.backend.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dssd.global.furniture.backend.model.Rol;
import dssd.global.furniture.backend.model.User;
import dssd.global.furniture.backend.repositories.UserRepository;
import dssd.global.furniture.backend.services.interfaces.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

   public boolean login(String username,String password) {
	   User u=this.userRepository.findByUsername(username);
	   if((u!=null) && (u.getPassword().equals(password))) {
		   return true;
	   }
	   return false;
   }

   @Override
	public Rol getRole(String username) {
	   User u=this.userRepository.findByUsername(username);
	   if(u!=null) {
		   return u.getRol();
	   }else {
		   return null;
	   }
}
}
