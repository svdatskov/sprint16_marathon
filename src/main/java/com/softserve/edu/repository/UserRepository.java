package com.softserve.edu.repository;


import com.softserve.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> getAllByRole(User.Role role);

    @Query(value = "select * from Users u where u.role = ?1", nativeQuery = true)
    List<User> findByRole(String role);

    List<User> findByRole(Role role);

    User findUserByEmail(String email);

}
