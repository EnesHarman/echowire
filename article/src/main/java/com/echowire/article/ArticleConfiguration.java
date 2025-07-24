package com.echowire.article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.echowire.user_preference.UserPreferenceServiceGrpc;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class ArticleConfiguration {

    @Bean
    public UserPreferenceServiceGrpc.UserPreferenceServiceBlockingStub userPreferenceServiceBlockingStub(GrpcChannelFactory channels) {
        return UserPreferenceServiceGrpc.newBlockingStub(channels.createChannel("userPreferenceService"));
    }
}
