package br.com.wepdev.enviopromocoesclientesjob.config;

import br.com.wepdev.enviopromocoesclientesjob.job.EnvioPromocoesClientesScheduleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuracao do Quartz
 */
@Configuration
public class QuartzConfig {


    /**
     * Metodo que contem as informaçoes do Job que sera executado
     * @return
     */
    @Bean
    public JobDetail quartzJobDetail(){
        return JobBuilder
                .newJob(EnvioPromocoesClientesScheduleJob.class) // Job que executa um job de forma agendada
                .storeDurably() // mantem os dados das execucoes agendadas, para nao perder os dados
                .build();
    }

    /**
     * Dispara a execucao do job (gatilho de execucao)
     * @return
     */
    @Bean
    public Trigger jobTrigger(){
        SimpleScheduleBuilder scheduleBuilder = // Agendador simples
                SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(60) // executa o job a cada 60 segundos
                        .withRepeatCount(2); // esse gatilho sera disparado 2 vezes + 1 , que é a vez inicial, somando o total de 3 vezes
        return TriggerBuilder // construcao da Trigger
                .newTrigger()
                .forJob(quartzJobDetail()) // informa o job que utilizara essa Trigger
                .withSchedule(scheduleBuilder)
                .build();
    }
}
