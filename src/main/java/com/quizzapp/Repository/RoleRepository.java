package com.quizzapp.Repository;

import com.quizzapp.Models.Erole;
import com.quizzapp.Models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    Optional<RoleEntity> findByRoleEnum(Erole roleEnum);

    List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
