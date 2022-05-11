<<<<<<< HEAD
package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuarioRepository.save(usuario));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				/**
				 * Retorna o objeto usarioLogin atualizado para a classe UsuarioController.
				 * A Classe controladora checará se deu tudo certo nesta operação e retornará
				 * o status.
				 */
				return usuarioLogin;

			}
		}	
		
		/**
		 * empty -> Retorna uma instância de Optional vazia, caso o usuário não seja encontrado.
		 */
		return Optional.empty();
		
	}

	/**
	*  Método Criptografar Senhas.
	*   
	*  Instancia um objeto da Classe BCryptPasswordEncoder para criptografar
	*  a senha do usuário.
	*
	*  O método encode retorna a senha criptografada no formato BCrypt. Para mais detalhes,
	*  consulte a documentação do BCryptPasswordEncoder.
	* 
	*/
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}
	
	/**
	*  Método Comparar Senhas.
	*   
	*  Checa se a senha enviada, depois de criptografada, é igual a senha
	*  gravada no Banco de Dados.
	* 
	*  Instancia um objeto da Classe BCryptPasswordEncoder para comparar
	*  a senha do usuário com a senha gravad no Banco de dados.
	*
	*  matches -> Verifca se a senha codificada obtida do banco de dados corresponde à 
	*  senha enviada depois que ela também for codificada. Retorna verdadeiro se as 
	*  senhas coincidem e falso se não coincidem.  
	* 
	*/
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

=======
package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

		if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();

		usuario.setSenha(criptografarSenha(usuario.getSenha()));

		return Optional.of(usuarioRepository.save(usuario));
	
	}

	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		if(usuarioRepository.findById(usuario.getId()).isPresent()) {
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.ofNullable(usuarioRepository.save(usuario));
			
		}

		return Optional.empty();
	
	}	

	public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

		Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

		if (usuario.isPresent()) {

			if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

				usuarioLogin.get().setId(usuario.get().getId());
				usuarioLogin.get().setNome(usuario.get().getNome());
				usuarioLogin.get().setFoto(usuario.get().getFoto());
				usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
				usuarioLogin.get().setSenha(usuario.get().getSenha());

				/**
				 * Retorna o objeto usarioLogin atualizado para a classe UsuarioController.
				 * A Classe controladora checará se deu tudo certo nesta operação e retornará
				 * o status.
				 */
				return usuarioLogin;

			}
		}	
		
		/**
		 * empty -> Retorna uma instância de Optional vazia, caso o usuário não seja encontrado.
		 */
		return Optional.empty();
		
	}

	/**
	*  Método Criptografar Senhas.
	*   
	*  Instancia um objeto da Classe BCryptPasswordEncoder para criptografar
	*  a senha do usuário.
	*
	*  O método encode retorna a senha criptografada no formato BCrypt. Para mais detalhes,
	*  consulte a documentação do BCryptPasswordEncoder.
	* 
	*/
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	}
	
	/**
	*  Método Comparar Senhas.
	*   
	*  Checa se a senha enviada, depois de criptografada, é igual a senha
	*  gravada no Banco de Dados.
	* 
	*  Instancia um objeto da Classe BCryptPasswordEncoder para comparar
	*  a senha do usuário com a senha gravad no Banco de dados.
	*
	*  matches -> Verifca se a senha codificada obtida do banco de dados corresponde à 
	*  senha enviada depois que ela também for codificada. Retorna verdadeiro se as 
	*  senhas coincidem e falso se não coincidem.  
	* 
	*/
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

>>>>>>> 9bac22a98c29277e7379e86231514a93c2ad92b0
}