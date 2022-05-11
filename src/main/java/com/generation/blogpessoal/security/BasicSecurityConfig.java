package com.generation.blogpessoal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe BasicSecurityConfig
 * 
 * Esta classe é responsável por habilitar a segurança básica da aplicação e o login
 * na aplicação.
 * 
 * Para habilitar a segurança HTTP no Spring, precisamos extender (herdar) 
 * a Classe WebSecurityConfigurerAdapter para fornecer uma configuração padrão 
 * no método configure (HttpSecurity http)
 * 
 * A configuração padrão garante que qualquer requisição enviada para a API 
 * seja autenticada com login baseado em formulário ou autenticação via Browser.
 * 
 * Para personalizar a autenticação utilizaremos a sobrecarga dos métodos da
 * Classe WebSecurityConfigurerAdapter
 * 
 */

/**
 *  A annotation @EnableWebSecurity: habilita a configuração de segurança padrão 
 *  do Spring Security na nossa api.
 */
@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * A annotation @Autowired insere uma Injeção de Dependência. 
	 * 
	 * Como iremos utilizar os usuários salvos no nosso Banco de dados,
	 * na tabela tb_usuarios, para efetuar o login na api precisamos injetar 
	 * um objeto da Interface UserDetailsService que será implementada na 
	 * Classe UserDetailsServiceImpl que fará o acesso ao nosso Banco de dados
	 * para recuperar os dados do usuário.
	 */

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 *  Sobrescreve (@Override) o primeiro método Configure, que tem a função 
	 *  de criar uma nova instância da Classe AuthenticationManagerBuilder e 
	 *  define que o login será efetuado através dos usuários criados no Banco de dados.
	 *  Para recuperar os dados do usuário no Banco de Dados utilizaremos a Interface 
	 *  UserDetailsService.
	 *  Outra alternativa de login seria acriação de um usuário em memória, que veremos nas
	 *  próximas sessões.
	 *  
	 *  O método é do tipo protected por definição da classe.
	 * 
	 *  Lembrete:
	 * 
	 *  1) public: permite acesso a qualquer código externo a classe.
	 *  2) protected: permite acesso às classes filhas, mas proíbe a qualquer 
	 *     outro acesso externo.
	 *  3) private: proíbe qualquer acesso externo à própria classe, inclusive 
	 *     das classes filhas.
	 */

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.userDetailsService(userDetailsService);

		 auth.inMemoryAuthentication()
			.withUser("root")
			.password(passwordEncoder().encode("root"))
			.authorities("ROLE_USER");

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 *  Sobrescreve (@Override) o segundo método Configure que é responsável por
	 *  criar uma instância da Classe HttpSecurity, que permite configurar a 
	 *  segurança baseada na web para solicitações http específicas (endpoints)
	 */
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/usuarios/logar").permitAll()
			.antMatchers("/usuarios/cadastrar").permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest().authenticated()
			.and().httpBasic()
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().cors()
			.and().csrf().disable();	
	}
}