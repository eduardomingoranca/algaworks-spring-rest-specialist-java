package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenAPI {

    @ApiOperation("Lista as permissoes associadas a um grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<PermissaoModel> listar(@ApiParam(value = "ID do grupo", required = true) Long id);

    @ApiOperation("Associacao de permissao com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(@ApiParam(value = "ID do grupo", required = true) Long id,
                                  @ApiParam(value = "ID da permissao", required = true) Long permissaoID);

    @ApiOperation("Desassociacao de permissao com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "ID do grupo", required = true) Long id,
                                     @ApiParam(value = "ID da permissao", required = true) Long permissaoID);

}
