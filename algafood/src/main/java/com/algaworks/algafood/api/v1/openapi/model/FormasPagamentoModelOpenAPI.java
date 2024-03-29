package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenAPI {
    private FormaPagamentoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("FormasPagamentoEmbeddedModel")
    @Data
    public static class FormaPagamentoEmbeddedModelOpenAPI {
        List<FormaPagamentoModel> formasPagamento;
    }

}
