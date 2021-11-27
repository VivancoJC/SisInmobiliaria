package com.proyecto.cliente.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.proyecto.servidor.model.Country;
import com.proyecto.servidor.model.Customer;
import com.proyecto.servidor.service.CountryService;
import com.proyecto.servidor.service.CustomerService;
import com.proyecto.servidor.service.EmailService;
import com.proyecto.servidor.web.dto.CustomerRegistrationDto;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        CustomerRegistrationDto customerRegistrationDto = new CustomerRegistrationDto();
        model.addAttribute("customerRegistrationDto", customerRegistrationDto);

        return "auth/login";
    }

    @GetMapping("/forget-password")
    public String forgetPassword(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        CustomerRegistrationDto customerRegistrationDto = new CustomerRegistrationDto();
        model.addAttribute("customerRegistrationDto", customerRegistrationDto);

        return "auth/forget-password";
    }

    @PostMapping("/forget-password")
    public String forgetPasswordRecovery(@Valid @ModelAttribute("customerRegistrationDto") CustomerRegistrationDto customerRegistrationDto, BindingResult result, Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        model.addAttribute("customerRegistrationDto", customerRegistrationDto);

        Customer customerExists = customerService.findByUsername(customerRegistrationDto.getUsername());

        //System.out.println("user-->"+userRegistrationDto.getUserName());
        System.out.println("customerExists-->"+customerExists);

        if (customerExists != null) {
            return "redirect:/forget-password?email";
        }
        if(result.hasErrors()){
            return "auth/forget-password";
        }
        //Send email
        return "redirect:/forget-password?success";

    }



    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        CustomerRegistrationDto customerRegistrationDto = new CustomerRegistrationDto();
        model.addAttribute("customerRegistrationDto", customerRegistrationDto);

        //Get countries list
        List<Country> countryList = countryService.findAll();
        model.addAttribute("countries", countryList);

        return "auth/register";
    }
    @PostMapping("/register")
    public String registerUserAccount(@Valid @ModelAttribute("customerRegistrationDto") CustomerRegistrationDto customerRegistrationDto, BindingResult result, HttpServletRequest request, Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        model.addAttribute("customerRegistrationDto", customerRegistrationDto);

        //Get countries list
        List<Country> countryList = countryService.findAll();
        model.addAttribute("countries", countryList);

        Customer customerExists = customerService.findByUsername(customerRegistrationDto.getUsername());

        //System.out.println("user-->"+userRegistrationDto.getUserName());
        System.out.println("customerExists-->"+customerExists);

        if (customerExists != null) {
            return "redirect:/register?email";
        }
        if(result.hasErrors()){
            return "auth/register";
        }

        Customer newCustomer= customerService.save(customerRegistrationDto);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        //send email
        emailService.registration(appUrl, newCustomer);

        return "redirect:/register?success";
    }
}
