package com.estudosmartins.alurafood.pagamentos.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagamentoAMQPConfiguration {
    //Aqui declaramos os recursos do RabbitMQ

    @Bean
    public Queue criarFila() {
        return QueueBuilder.nonDurable("pagamento.concluido").build();  //Durável: A fila vai existir mesmo se o broker reiniciar ou cair?
    }

    //Pega os recursos declarados e envia para o broker RabbitMQ
    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    //Força o RabbitAdmin a subir os recursos pro broker. Evento disparado após toda a aplicação subir
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
