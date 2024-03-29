package com.algaworks.algafood.core.storage.configuration;

import com.algaworks.algafood.core.storage.properties.StorageProperties;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.local.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.s3.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.algaworks.algafood.core.storage.properties.StorageProperties.TipoStorage.S3;
import static com.amazonaws.services.s3.AmazonS3ClientBuilder.standard;

@Configuration
public class StorageConfig {
    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (S3.equals(storageProperties.getTipo()))
            return new S3FotoStorageService();
        else
            return new LocalFotoStorageService();
    }

}
