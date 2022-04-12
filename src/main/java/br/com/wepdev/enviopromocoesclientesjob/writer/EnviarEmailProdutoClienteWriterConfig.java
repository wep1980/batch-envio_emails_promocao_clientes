package br.com.wepdev.enviopromocoesclientesjob.writer;

import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.batch.item.mail.builder.SimpleMailMessageItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

/**
 * Classe escritora do email
 */
@Configuration
public class EnviarEmailProdutoClienteWriterConfig {


    /**
     * MailSender -> Ele foi carregado automaticamente pelo spring com as propriedades configuradas no application.properties
     * @param mailSender
     * @return
     */
    @Bean
    public SimpleMailMessageItemWriter enviarEmailProdutoClienteWriter(MailSender mailSender){

        return new SimpleMailMessageItemWriterBuilder()
                .mailSender(mailSender)
                .build();

    }
}
