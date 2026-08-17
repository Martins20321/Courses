package br.com.alurafood.avaliacao.avaliacao.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvaliacaoAMQPConfiguration {
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue filaDetalhesAvaliacao() {
        return QueueBuilder
                .durable("pagamentos.detalhes-avaliacao")
                .quorum()  //Usando uma quorum queue = o conteudo das filas sera replicado
                .withArgument("x-quorum-initial-group-size", 3)
                .deadLetterExchange("pagamentos.dlx") //Em caso de erro, envia para nossa DLX e envia para a DLQ
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder
                .fanoutExchange("pagamentos.ex")
                .build();
    }

    @Bean
    public Binding bindPagamentoPedido(Queue filaDetalhesAvaliacao, FanoutExchange fanoutExchange) {
        return BindingBuilder
                .bind(filaDetalhesAvaliacao)
                .to(fanoutExchange);
    }

    //DLX
    @Bean
    public FanoutExchange fanoutExchangeDLQ() {
        return ExchangeBuilder.fanoutExchange("pagamentos.dlx").build();
    }

    //DLQ
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.nonDurable("pagamentos.detalhes-avaliacao-dlq").build();
    }

    //Biding da DLX com a DLQ
    @Bean
    public Binding bindingDlxPagamentoPedido(Queue deadLetterQueue, FanoutExchange fanoutExchangeDLQ) {
        return BindingBuilder.bind(deadLetterQueue).to(fanoutExchangeDLQ);
    }

    @Bean
    public RabbitAdmin criaRabbitAdmin(ConnectionFactory conn) {
        return new RabbitAdmin(conn);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializaAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

}
