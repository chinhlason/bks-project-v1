package com.bksproject.bksproject.Repository;


import com.bksproject.bksproject.Enum.User_roles;
import com.bksproject.bksproject.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(User_roles name);
}
