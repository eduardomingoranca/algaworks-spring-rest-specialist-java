package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuarios")
public interface UsuarioGrupoControllerOpenAPI {

    @ApiOperation("Lista os grupos associadas ao usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<GrupoModel> listar(@ApiParam(value = "Codigo de um usuario", required = true) Long id);

    @ApiOperation("Associacao de usuario com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(@ApiParam(value = "Codigo de um usuario", required = true) Long id,
                                  @ApiParam(value = "Codigo do grupo", required = true) Long grupoID);

    @ApiOperation("Desassociacao de usuario com grupo")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(@ApiParam(value = "Codigo de um usuario", required = true) Long id,
                                     @ApiParam(value = "Codigo do grupo", required = true) Long grupoID);

}
