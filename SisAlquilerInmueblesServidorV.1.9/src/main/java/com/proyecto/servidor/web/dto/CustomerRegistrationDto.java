package com.proyecto.servidor.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDto {
	@NotEmpty(message = "El nombre no puede estar vacío!")
	private String firstName;

	@NotEmpty(message = "El apellido no puede estar vacío!")
	private String lastName;

	@NotEmpty(message = "El correo electrónico no puede estar vacío!")
	@Email(message = "*Proporcione un correo electrónico válido")
	private String username;

	@Length(min = 5, message = "*Tu contraseña debe tener al menos 5 caracteres")
	@NotEmpty(message = "*Por favor ingrese su contraseña")
	private String password;

	@NotEmpty(message = "*Por favor proporcione su teléfono")
	private String phone;

	@NotEmpty(message = "Confirmar contraseña es obligatorio")
	private String confirm;

	private String company;
	private String address1;
	private String address2;
	private String city;
//	private String state;
	private String country;
//	private String postalCode;
}
