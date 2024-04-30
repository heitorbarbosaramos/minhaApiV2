package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.enums.UsuarioStatus;
import com.heitor.minhaApi.repostirory.UsuarioRepository;
import com.heitor.minhaApi.security.LoginService;
import com.heitor.minhaApi.security.UsuarioLoginDTO;
import com.heitor.minhaApi.security.feignClient.TokenAdminResponse;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import com.heitor.minhaApi.service.utils.CorpoEmail;
import com.heitor.minhaApi.service.utils.EmailSenderService;
import com.heitor.minhaApi.service.utils.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final EnderecoService enderecoService;
    private final EmailSenderService emailService;
    private final LoginService loginService;

    @Value("${bucket.front.url}")
    private String bucketFront;
    @Value("${cookies.name.token}")
    private String tokenName;

    public Usuario save(Usuario usuario){

        if(usuario.getEndereco() != null){
            usuario.setEndereco(enderecoService.save(usuario.getEndereco()));
        }
        return repository.save(usuario);
    }

    public Usuario findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Transactional
    public void createStep1(Usuario usuario){

        LocalDateTime dataLimite = LocalDateTime.now().plusMinutes(30);
        long timestamp = dataLimite.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli();

        usuario.setCodigoConfirmacao(StringUtils.geradorCaracteresNumericos(5));
        usuario.setTimestampRecuperaSenha(timestamp);
        usuario.setStatus(UsuarioStatus.ATIVACAO);

        usuario = save(usuario);
        
        String linkStep1 = bucketFront + "/ativar/" + timestamp + "/" + usuario.getCodigoConfirmacao();

        String corpoEmail = CorpoEmail.criarConta(usuario.getNome(), usuario.getEmail(), dataLimite, usuario.getCodigoConfirmacao(), linkStep1);
        emailService.enviaEmail(usuario.getEmail(), "Criação de Conta STEP 1", corpoEmail);
    }

    public UserRepresentarioKeyCloak createStep2(String timeStamp, String codigoConfirmacao, String confirmadoVia){

        Instant instant = Instant.ofEpochMilli(Long.parseLong(timeStamp));
        LocalDateTime dataTimeStamp = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        Long timeStampLong = Long.parseLong(timeStamp);

        Usuario usuario = findByCodConfirmacaoTimeStamp(timeStampLong, codigoConfirmacao);

        if(usuario == null){
            throw new IllegalArgumentException("Não existe um usuário atrelado a esse link de ativação");
        }

        switch (confirmadoVia){
            case "email":
                usuario.setStatus(UsuarioStatus.ATIVADO_EMAIL);
                break;
            case "sms":
                usuario.setStatus(UsuarioStatus.ATIVADO_SMS);
                break;
            default:
                throw new IllegalArgumentException("Forma de ativação é inválida");

        }

        usuario = save(usuario);

        if(dataTimeStamp.isBefore(LocalDateTime.now())){
            repository.delete(usuario);
            throw new IllegalArgumentException("A ativação foi cancelada pois o link de ativação execedeu o tempo limite.");
        }

        UserRepresentarioKeyCloak user = new UserRepresentarioKeyCloak();
        user.setFirstName(usuario.getNome());
        user.setLastName(usuario.getSobreNome());
        user.setEmail(usuario.getEmail());
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setUsername(usuario.getEmail().substring(0, usuario.getEmail().indexOf("@")));

        return user;
    }

    public UserRepresentarioKeyCloak createStep3(UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response){

        Usuario usuario = findByEmail(user.getEmail());

        findByCodConfirmacaoTimeStamp(usuario.getTimestampRecuperaSenha(), usuario.getCodigoConfirmacao());

        user = loginService.createUser(user, request, response);

        usuario.setIdUsuarioKeycloak(UUID.fromString(user.getId()));
        usuario.setTimestampRecuperaSenha(null);
        usuario.setCodigoConfirmacao(null);
        save(usuario);

        return user;
    }

    private Usuario findByCodConfirmacaoTimeStamp(Long timeStamp, String codigoConfirmacao){
        return repository.findByTimestampRecuperaSenhaAndCodigoConfirmacaoAndStatus(timeStamp, codigoConfirmacao, UsuarioStatus.ATIVACAO);
    }
}
