package com.proyecto.servidor.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

	@NotEmpty(message = "El nombre no puede estar vacío!")
	private String firstName;

	@NotEmpty(message = "El apellido no puede estar vacío!")
	private String lastName;

	@Email(message = "*Proporcione un correo electrónico válido")
	private String username;

	@Length(min = 5, message = "*Tu contraseña debe tener al menos 5 caracteres")
	@NotEmpty(message = "*Por favor proporcione su contraseña")
	private String password;
}
