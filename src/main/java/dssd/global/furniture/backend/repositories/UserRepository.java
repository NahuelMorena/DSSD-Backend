package dssd.global.furniture.backend.repositories;

import org.springframework.data.repository.CrudRepository;

import dssd.global.furniture.backend.model.User;

public interface UserRepository extends CrudRepository<User,Long> {

	public User findByUsername(String username); 
}
