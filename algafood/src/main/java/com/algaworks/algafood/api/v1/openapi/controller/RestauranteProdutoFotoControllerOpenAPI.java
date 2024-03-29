package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestauranteProdutoFotoControllerOpenAPI {

    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante nao encontrado", response = Problem.class)
    })
    FotoProdutoModel atualizarFoto(@ApiParam(value = "ID do restaurante", required = true) Long id,
                                   @ApiParam(value = "ID do produto", required = true) Long produtoId,
                                   FotoProdutoInput fotoProdutoInput,
                                   @ApiParam(value = "Arquivo da foto do produto (maximo 500KB, apenas JPG e PNG)",
                                           required = true) MultipartFile arquivo) throws IOException;


    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto invalido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto nao encontrada", response = Problem.class)
    })
    FotoProdutoModel buscarFoto(@ApiParam(value = "ID do restaurante", required = true) Long id,
                                @ApiParam(value = "ID do produto", required = true) Long produtoId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
    ResponseEntity<Object> servirFoto(Long id, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @ApiOperation(value = "Excluir a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto invalido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto do produto nao encontrada", response = Problem.class)
    })
    ResponseEntity<FotoProdutoModel> remover(@ApiParam(value = "ID do restaurante", required = true) Long id,
                                             @ApiParam(value = "ID do produto", required = true) Long produtoId);

}
