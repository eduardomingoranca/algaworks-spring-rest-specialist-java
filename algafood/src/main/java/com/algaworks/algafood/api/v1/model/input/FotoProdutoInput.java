package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.annotation.FileContentType;
import com.algaworks.algafood.core.validation.annotation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Getter
@Setter
public class FotoProdutoInput {
    @ApiModelProperty(hidden = true)
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @ApiModelProperty(example = "Descricao da foto do produto", required = true)
    @NotBlank
    private String descricao;

}
