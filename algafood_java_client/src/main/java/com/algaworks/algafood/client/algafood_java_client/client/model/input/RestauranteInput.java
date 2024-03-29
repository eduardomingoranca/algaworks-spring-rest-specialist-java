package com.algaworks.algafood.client.algafood_java_client.client.model.input;

import com.algaworks.algafood.client.algafood_java_client.client.model.input.id.CozinhaIDInput;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteInput {
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaIDInput cozinha;
    private EnderecoInput endereco;

}
