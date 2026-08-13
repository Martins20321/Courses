package br.com.alurafood.pedidos.amqp;

import br.com.alurafood.pedidos.event.PagamentoConcluidoEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PagamentoListener {

    //Metodo para receber as mensagens
    @RabbitListener(queues = "pagamentos.detalhes-pedido") //Quero receber mensagens dessa fila. Listener = Se torna um ouvinte desta fila
    public void receberMensagens(@Payload PagamentoConcluidoEvent pagamento) {
        //Formatação da mensagem, como vou receber
        String message = """
                Dados do Pagamento: %s
                Número do pedido: %s
                Valor R$: %s
                Status: %s
                """.formatted(pagamento.id(), pagamento.pedidoId(), pagamento.valor(), pagamento.status());
        System.out.println("Recebido a mensagem " + message);
    }
}
