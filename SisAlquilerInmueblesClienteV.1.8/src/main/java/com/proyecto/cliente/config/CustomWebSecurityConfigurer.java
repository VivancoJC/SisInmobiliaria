package com.proyecto.cliente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.proyecto.servidor.service.ReservaItemService;
import com.proyecto.servidor.service.CategoryService;
import com.proyecto.servidor.service.CiudadService;
import com.proyecto.servidor.service.CustomerService;
import com.proyecto.servidor.service.EmailService;
import com.proyecto.servidor.service.EmpresaService;
import com.proyecto.servidor.service.ContratoService;
import com.proyecto.servidor.service.OrderDetailService;
import com.proyecto.servidor.service.OrderService;
import com.proyecto.servidor.service.ProductService;
import com.proyecto.servidor.service.VerificarCartService;
import com.proyecto.servidor.service.UserService;
import com.proyecto.servidor.service.impl.CustomerServiceImpl;
import com.proyecto.servidor.service.impl.UserServiceImpl;
import com.proyecto.servidor.util.Utility;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomerService customerService(){
        return new CustomerServiceImpl();
    }

    @Bean
    public ProductService productService(){
        return new ProductService();
    }

    @Bean
    public CategoryService categoryService(){
        return new CategoryService();
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public CiudadService countryService(){
        return new CiudadService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OrderService orderService(){
        return new OrderService();
    }

    @Bean
    public OrderDetailService orderDetailService(){
        return new OrderDetailService();
    }

    @Bean
    public VerificarCartService shoppingCartService(){
        return new VerificarCartService();
    }

    @Bean
    public ReservaItemService cartItemService(){
        return new ReservaItemService();
    }

    @Bean
    public EmpresaService makeService(){
        return new EmpresaService();
    }

    @Bean
    public ContratoService modelService(){
        return new ContratoService();
    }

    @Bean
    public Utility utility(){
        return new Utility();
    }

    @Bean
    public EmailService emailService(){
        return new EmailService();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/image/**",
            "/ico/**",

            "/",
            "/category",
            "/part-search",
            "/part-details",



            "/register",
            "/login",
            "/forget-password",

            "/about-us",
            "/contact-us",

            "/faq",
            "/privacy-policy",
            "/return-policy",
            "/terms-and-conditions",

            //api route
            "/models",

            //Secure pages
            /*
             "/view-cart",
             "/checkout",
             "/my-account",
            "/order-details",
            "/order-history",
            "/change-password",*/
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().
                antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/login?error")
                .defaultSuccessUrl("/")
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout")//.deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }

    public void configure(WebSecurity web) throws Exception {
        // web.ignoring().antMatchers("/resources/static/**").anyRequest();
    }
}
