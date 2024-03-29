package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenAPI {
    @Autowired
    private CadastroPermissaoService permissaoService;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> todasPermissoes = permissaoService.listar();

        return permissaoModelAssembler.toCollectionModel(todasPermissoes);
    }

}
