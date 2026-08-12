package br.com.alurafood.pedidos.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PagamentoListener {

    //Metodo para receber as mensagens
    @RabbitListener(queues = "pagamento.concluido") //Quero receber mensagens dessa fila. Listener = Se torna um ouvinte desta fila
    public void receberMensagens(Message message) {
        System.out.println("Recebido a mensagem " + message.toString());
    }
}
