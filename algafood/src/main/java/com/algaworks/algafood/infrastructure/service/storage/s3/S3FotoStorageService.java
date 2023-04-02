package com.algaworks.algafood.infrastructure.service.storage.s3;

import com.algaworks.algafood.core.storage.properties.StorageProperties;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;
import static java.lang.String.format;

public class S3FotoStorageService implements FotoStorageService {
    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString()).build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            // criando o objeto de requisicao
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                    // adicionando o acesso publico de leitura
                    .withCannedAcl(PublicRead);

            // adicionando o objeto no bucket
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Nao foi possivel enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}
