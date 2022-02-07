package com.iexec.common.utils;

import feign.Feign;
import feign.Logger;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.Encoder;
import feign.codec.StringDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Returns a Feign builder with shared configuration to create various Feign client instances.
 * <p>
 * The shared configuration is:
 * <ul>
 * <li>JacksonEncoder with overridden encode method to deal with strings
 * <li>JacksonDecoder with overridden decode method to deal with strings
 * <li>SLF4J logger enabled with FULL details. Logs are displayed by configuring the log level on a target interface logger.
 * <li>application/json Content-Type header
 * </ul>
 */
public class FeignBuilder {

    private FeignBuilder() {};

    public static Feign.Builder createBuilder(Logger.Level logLevel) {
        return Feign.builder()
                .encoder(new JacksonEncoder() {
                    @Override
                    public void encode(Object object, Type bodyType, RequestTemplate template) {
                        if (bodyType == String.class) {
                            new Encoder.Default().encode(object, bodyType, template);
                        } else {
                            super.encode(object, bodyType, template);
                        }
                    }
                })
                .decoder(new JacksonDecoder() {
                    @Override
                    public Object decode(Response response, Type type) throws IOException {
                        if (type == String.class) {
                            return new StringDecoder().decode(response, type);
                        } else {
                            return super.decode(response, type);
                        }
                    }
                })
                .logger(new Slf4jLogger())
                .logLevel(logLevel)
                .requestInterceptor(template -> template.header("Content-Type", "application/json"));
    }

}
