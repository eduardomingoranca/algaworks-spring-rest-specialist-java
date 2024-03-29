package com.algaworks.algafood.api.v2.model.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "CozinhaInput")
@Setter
@Getter
public class CozinhaInputVersionTwo {

    @ApiModelProperty(example = "Argentina", required = true)
    @NotBlank
    private String nomeCozinha;

}
