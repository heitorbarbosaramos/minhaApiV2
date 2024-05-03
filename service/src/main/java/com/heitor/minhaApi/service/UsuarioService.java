package com.heitor.minhaApi.service;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.enums.UsuarioStatus;
import com.heitor.minhaApi.repostirory.UsuarioRepository;
import com.heitor.minhaApi.security.LoginService;
import com.heitor.minhaApi.security.UsuarioLoginDTO;
import com.heitor.minhaApi.security.dto.UsuarioCreateDTO;
import com.heitor.minhaApi.security.feignClient.TokenAdminResponse;
import com.heitor.minhaApi.security.feignClient.UserRepresentarioKeyCloak;
import com.heitor.minhaApi.security.feignClient.UserResetSenha;
import com.heitor.minhaApi.service.utils.CorpoEmail;
import com.heitor.minhaApi.service.utils.DataHoraUtils;
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

import java.io.IOException;
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
    @Value("${bucket.front.url.resetPassword}")
    private String bucketFrontResetPassword;
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
    public void createStep1(UsuarioCreateDTO dto){

        long timestamp = DataHoraUtils.criarTimeStampDataHoraAtual(30);

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setSobreNome(dto.getSobreNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEmail(dto.getEmail());

        usuario.setCodigoConfirmacao(StringUtils.geradorCaracteresNumericos(5));
        usuario.setTimestampRecuperaSenha(timestamp);
        usuario.setStatus(UsuarioStatus.ATIVACAO);

        usuario = save(usuario);
        
        String linkStep1 = bucketFront + "/ativar/" + timestamp + "/" + usuario.getCodigoConfirmacao();

        String corpoEmail = CorpoEmail.criarConta(usuario.getNome(),
                    usuario.getEmail(),
                    LocalDateTime.now().plusMinutes(30),
                    usuario.getCodigoConfirmacao(), linkStep1);
        emailService.enviaEmail(usuario.getEmail(), "Criação de Conta STEP 1", corpoEmail);
    }

    public void createStep2(String timeStamp, String codigoConfirmacao, String confirmadoVia, HttpServletRequest request, HttpServletResponse response) throws IOException {

        LocalDateTime dataTimeStamp = DataHoraUtils.convertDataTimeFromTimeStampUTC(timeStamp);

        Long timeStampLong = Long.parseLong(timeStamp);

        Usuario usuario = findByCodConfirmacaoTimeStamp(timeStampLong, codigoConfirmacao, UsuarioStatus.ATIVACAO);

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

        usuario.setTimestampRecuperaSenha(DataHoraUtils.criarTimeStampDataHoraAtual(30));
        usuario.setCodigoConfirmacao(StringUtils.geradorCaracteresNumericos(5));

        usuario = save(usuario);

        if(dataTimeStamp.isBefore(LocalDateTime.now())){
            repository.delete(usuario);
            throw new IllegalArgumentException("A ativação foi cancelada pois o link de ativação execedeu o tempo limite.");
        }

        response.sendRedirect(bucketFront+"/step3"+"?tr="+usuario.getTimestampRecuperaSenha()+"&cf="+usuario.getCodigoConfirmacao()+"&ativado="+ usuario.getStatus().name());
    }

    public UserRepresentarioKeyCloak creatStep3(Long timeStamp, String codigoConfirmacao, String ativado){

        Usuario usuario = findByCodConfirmacaoTimeStamp(timeStamp, codigoConfirmacao, UsuarioStatus.valueOf(ativado));

        UserRepresentarioKeyCloak user = new UserRepresentarioKeyCloak();
        user.setFirstName(usuario.getNome());
        user.setLastName(usuario.getSobreNome());
        user.setEmail(usuario.getEmail());
        user.getRealmRoles().add("USUARIO");
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setUsername(usuario.getEmail().substring(0, usuario.getEmail().indexOf("@")));

        return user;


    }

    public void createStep4(UserRepresentarioKeyCloak user, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Usuario usuario = findByEmail(user.getEmail());

        findByCodConfirmacaoTimeStamp(usuario.getTimestampRecuperaSenha(), usuario.getCodigoConfirmacao(), usuario.getStatus());

        user = loginService.createUser(user, request, response);

        usuario.setIdUsuarioKeycloak(UUID.fromString(user.getId()));
        usuario.setTimestampRecuperaSenha(DataHoraUtils.criarTimeStampDataHoraAtual(30));
        usuario.setCodigoConfirmacao(StringUtils.geradorCaracteresNumericos(5));
        save(usuario);

        response.sendRedirect(bucketFront+bucketFrontResetPassword+"?tr="+usuario.getTimestampRecuperaSenha()+"&cf="+usuario.getCodigoConfirmacao()+"&ativado="+ usuario.getStatus().name());
    }

    public void createStep5(String timeStamp, UserResetSenha rest, String codigoConfirmacao, String ativado, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = findByCodConfirmacaoTimeStamp(Long.parseLong(timeStamp), codigoConfirmacao, UsuarioStatus.valueOf(ativado));
        loginService.resetSenha(usuario.getIdUsuarioKeycloak().toString(), rest, request, response);
        response.sendRedirect(bucketFront);
    }

    private Usuario findByCodConfirmacaoTimeStamp(Long timeStamp, String codigoConfirmacao, UsuarioStatus status){
        Usuario usuario = repository.findByTimestampRecuperaSenhaAndCodigoConfirmacaoAndStatus(timeStamp, codigoConfirmacao, status);
        if(usuario == null){
            throw new IllegalArgumentException("Não existe um usuário atrelado a esse link de ativação");
        }
        return usuario;
    }
}
