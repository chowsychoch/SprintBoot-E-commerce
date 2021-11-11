package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 2);
		User user_name = new User("cho@gmail.com", "name2021", "cho","man");
		user_name.addRole(roleAdmin);
		
		User savedUser = repo.save(user_name);
		assertThat(savedUser.getId()).isGreaterThan(2);
		
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		Role roleAdmin = new Role(4);
		Role roleSalesperson = new Role(5);
		User user_name = new User("josephine@gmail.com", "joj2020", "josephine","chow");
		
		user_name.addRole(roleSalesperson);
		user_name.addRole(roleAdmin);
		
		User savedUser = repo.save(user_name);
		assertThat(savedUser.getId()).isGreaterThan(2);
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User user_name = repo.findById(3).get();
		System.out.println(user_name);
		assertThat(user_name).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User user_name = repo.findById(3).get();
		user_name.setEnabled(true);
		user_name.setEmail("heyhey@gmail.com");
		repo.save(user_name);
	}
	
	@Test
	public void testUpdateUserRole() {
		User user_name = repo.findById(3).get();
		Role roleSalesperson = new Role(5);
		Role roleAdmin = new Role(4);
		user_name.getRoles().remove(roleSalesperson);
		user_name.addRole(roleAdmin);
		repo.save(user_name);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 5;
		repo.deleteById(userId);		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "chomancho@gmail.com";
		User user = repo.getUserByEmail(email);
		assertThat(user).isNotNull();		
	}
}
