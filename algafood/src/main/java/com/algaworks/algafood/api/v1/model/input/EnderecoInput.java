package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EnderecoInput {

    @ApiModelProperty(example = "38400-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "600", required = true)
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Apto 704")
    private String complemento;

    @ApiModelProperty(example = "Brasil", required = true)
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIDInput cidade;

}
