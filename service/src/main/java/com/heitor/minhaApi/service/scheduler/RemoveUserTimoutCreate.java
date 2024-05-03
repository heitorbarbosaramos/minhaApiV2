package com.heitor.minhaApi.service.scheduler;

import com.heitor.minhaApi.entity.Usuario;
import com.heitor.minhaApi.service.UsuarioService;
import com.heitor.minhaApi.service.utils.CorpoEmail;
import com.heitor.minhaApi.service.utils.DataHoraUtils;
import com.heitor.minhaApi.service.utils.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class RemoveUserTimoutCreate {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EmailSenderService emailSenderService;

    @Value("${bucket.front.url}")
    private String bucketFront;

    @Scheduled(cron = "*/30 * * * * *")
    public void deleteUserTimoutCreat() {
        log.info("SCHEDULER VERIFICA USUARIOS COM TEMPO EXECEDENTE DE CRIACAO");

        List<Usuario> usuarios = usuarioService.findTimeOutCreateUser();


        for(Usuario x : usuarios){
            String corpoEmail = CorpoEmail.removeUerTimeOutCreate(x.getNome(), x.getEmail(), DataHoraUtils.convertDataTimeFromTimeStampAmericaSaoPaulo(x.getTimestampRecuperaSenha().toString()), bucketFront);
            usuarioService.deteleUser(x.getId());
            emailSenderService.enviaEmail(x.getEmail(), "SOLICITAÇÃO DE CONTA EXPITADO", corpoEmail);
        }
        log.info("FIM SCHEDULER VERIFICA USUARIOS COM TEMPO EXECEDENTE DE CRIACAO");
    }
}
