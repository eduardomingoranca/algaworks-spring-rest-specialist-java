package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.util.ReflectionUtils.*;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

//    API de validacao de classe e atributos
    @Autowired
    private SmartValidator validator;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        List<Restaurante> restaurantes = cadastroRestaurante.listar();

        return status(OK).body(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        RestauranteModel restauranteModel = null; // conversao da entidade Restaurante para RestauranteModel

        return restauranteModel;
    }

//     @Valid -> anotacao valida a entrada de dados na instancia da classe antes de executar o metodo.
//    @Validated -> quando for cadastrar um restaurante sera validado um objeto do tipo restaurante que possuem
//    constraints do grupo cadastro restaurante.
    @PostMapping
    @ResponseStatus(CREATED)
    public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return cadastroRestaurante.salvar(restaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable("restauranteId") Long id, @RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
            copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
                    "endereco", "dataCadastro", "produtos");

            return cadastroRestaurante.salvar(restauranteAtual);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable("restauranteId") Long id,
                                        @RequestBody Map<String, Object> campos, HttpServletRequest request) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
        merge(campos, restauranteAtual, request);

        validate(restauranteAtual, "restaurante");

        return atualizar(id, restauranteAtual);
    }

    private void validate(Restaurante restaurante, String objectName) {
        // bindingResult -> dentro dele tem os erros de validacao
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors())
            throw new ValidacaoException(bindingResult);
    }

//     mesclar os valores do map campos para dentro do restaurante atual
    private static void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
                              HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest  = new ServletServerHttpRequest(request);

        try {
//         ObjectMapper -> class responsavel por converter objetos java em json e json em objetos java
            ObjectMapper objectMapper = new ObjectMapper();
//        configurando o object mapper para quando uma propriedade for invalida
            objectMapper.configure(FAIL_ON_IGNORED_PROPERTIES, true);
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

            dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//             obtendo os campos de restaurante e informado no nome da propriedade informada
                Field field = findField(Restaurante.class, nomePropriedade);
//             acessando um atributo/metodo private
                assert field != null;
                field.setAccessible(true);

//             obtendo o valor da propriedade convertido
                Object novoValor = getField(field, restauranteOrigem);

//             alterando o valor da variavel de instancia informada pelo valor da propriedade
                setField(field, restauranteDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
//            capturando IllegalArgumentException e relanca a exception HttpMessageNotReadableException
            Throwable rootCause = getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

}
