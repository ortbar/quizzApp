package com.quizzapp;

import com.quizzapp.Models.Erole;
import com.quizzapp.Models.PermissionEntity;
import com.quizzapp.Models.RoleEntity;
import com.quizzapp.Models.UserEntity;
import com.quizzapp.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class QuizzappApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuizzappApplication.class, args);
	}

	// esto se ejecuta al levantarse la aplicacion. lo usamos para poblar la bd
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {


			/* CREAR PERMISOS */

			PermissionEntity playPermission = PermissionEntity.builder()
					.name("JUGAR")
					.build();

			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();



			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			/* CREATE ROLES */

			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(Erole.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission,playPermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(Erole.USER)
					.permissionList(Set.of(playPermission,readPermission))
					.build();





			/* CREATE USERS */

			UserEntity userAlejandro = UserEntity.builder()
					.username("alejandro")
					.password("$2a$10$/D0YNdddt6lDr3gK2jigc.yHJvWBO3.GOlYDL5g4bJAD7XFXHBj.S")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialNotExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userPepito = UserEntity.builder()
					.username("pepito")
					.password("$2a$10$/D0YNdddt6lDr3gK2jigc.yHJvWBO3.GOlYDL5g4bJAD7XFXHBj.S")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialNotExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userCloti = UserEntity.builder()
					.username("cloti")
					.password("$2a$10$/D0YNdddt6lDr3gK2jigc.yHJvWBO3.GOlYDL5g4bJAD7XFXHBj.S")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialNotExpired(true)
					.roles(Set.of(roleUser))
					.build();

			UserEntity userVicato = UserEntity.builder()
					.username("vicato")
					.password("$2a$10$/D0YNdddt6lDr3gK2jigc.yHJvWBO3.GOlYDL5g4bJAD7XFXHBj.S")
					.isEnabled(true)
					.accountNotExpired(true)
					.accountNotLocked(true)
					.credentialNotExpired(true)
					.roles(Set.of(roleAdmin))
					.build();

			userRepository.saveAll(List.of(userAlejandro, userPepito, userCloti, userVicato));



		};



	}


}
