package com.algaworks.algafood.api.v2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@ApiModel(value = "CidadeModel", description = "Representa uma cidade")
@Setter
@Getter
public class CidadeModelVersionTwo extends RepresentationModel<CidadeModelVersionTwo> {
    @ApiModelProperty(example = "1")
    private Long idCidade;

    @ApiModelProperty(example = "Uberlandia")
    private String nomeCidade;

    private Long idEstado;

    private String nomeEstado;

}
