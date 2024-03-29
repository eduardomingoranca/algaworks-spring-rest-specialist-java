package com.algaworks.algafood.api.v1.assembler.model;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.*;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (podeSerConfirmado(pedido))
            pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));

        if (podeSerCancelado(pedido))
            pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));

        if (podeSerEntregue(pedido))
            pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));

        pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoModel.getCliente().add(algaLinks.linkToCliente(pedido.getCliente().getId()));

        // Passando null no segundo argumento, porque eh indiferente para a
        // construcao da URL do recurso de forma de pagamento
        pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega()
                .getCidade().getId()));

        pedidoModel.getItens().forEach(item -> item.add(algaLinks.linkToProduto(pedidoModel.getRestaurante().getId(),
                item.getProdutoId(), "produto")));

        return pedidoModel;
    }

    private boolean podeSerConfirmado(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(CONFIRMADO);
    }

    private boolean podeSerEntregue(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(ENTREGUE);
    }

    private boolean podeSerCancelado(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(CANCELADO);
    }

}
