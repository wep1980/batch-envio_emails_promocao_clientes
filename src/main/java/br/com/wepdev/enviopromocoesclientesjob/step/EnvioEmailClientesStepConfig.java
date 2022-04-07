package br.com.wepdev.enviopromocoesclientesjob.step;

import br.com.wepdev.enviopromocoesclientesjob.dominio.InteresseProdutoCliente;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EnvioEmailClientesStepConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step enviarEmailClientesStep(
            ItemReader<InteresseProdutoCliente> lerInteresseProdutoClienteReader, // Lendo do banco de dados
            ItemProcessor<InteresseProdutoCliente, SimpleMailMessage> processarEmailProdutoClienteProcessor, // Processamento que trasnforma esse item lido do banco em uma mensagem de email
            ItemWriter<SimpleMailMessage> enviarEmailProdutoClienteWriter // O escritor envia o email
    ){
        return stepBuilderFactory
                .get("enviarEmailClientesStep")
                .<InteresseProdutoCliente, SimpleMailMessage>chunk(1)
                .reader(lerInteresseProdutoClienteReader)
                .processor(processarEmailProdutoClienteProcessor)
                .writer(enviarEmailProdutoClienteWriter)
                .build();
    }
}
