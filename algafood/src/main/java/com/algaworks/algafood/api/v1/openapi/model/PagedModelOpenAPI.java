package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenAPI<T> {
    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantidade de registros por pagina")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total de paginas")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Numero da pagina (comeca em 0)")
    private Long number;

}
