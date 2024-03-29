package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PedidoSpecs {

    // specification encapsula as restricoes/filtros
    public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
        return (root, query, builder) -> {
            if (Pedido.class.equals(query.getResultType())) {
                // agrupando em uma unica busca
                root.fetch("restaurante").fetch("cozinha");
                root.fetch("cliente");
            }

            // criando uma lista de filtros
            List<Predicate> predicates = new ArrayList<>();

            // adicionar predicates/filtros no arraylist
            if (filtro.getClienteId() != null)
                predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));

            if (filtro.getRestauranteId() != null)
                predicates.add(builder.equal(root.get("restaurante"), filtro.getRestauranteId()));

            // data de criacao do pedido deve ser maior que a data de criacao de inicio
            if (filtro.getDataCriacaoInicio() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                        filtro.getDataCriacaoInicio()));

            // data de criacao do pedido deve ser menor que a data de criacao final
            if (filtro.getDataCriacaoFim() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                        filtro.getDataCriacaoFim()));

            // retornando a lista de predicates/filtros
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
