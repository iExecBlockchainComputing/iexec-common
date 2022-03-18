package com.iexec.common.utils;

import feign.Feign;
import feign.Logger;
import feign.RequestTemplate;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
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
 * This builder allows to create Feign client instances which can manipulate {@code String} literals as well as
 * {@code Object} instances. Currently, there is no existing encoder/decoder pair which allows to manipulate both
 * data types.
 * <p>
 * We have:
 * <ul>
 * <li>{@code JacksonEncoder} and {@code JacksonDecoder} classes, they work well for JSON (de)serialization of
 *     {@code Object} instances. Those classes do not work well with {@code String} objects.
 * <li>Default Feign encoder and decoder classes (respectively {@code Encoder.Default} and {@code StringDecoder}), they
 *     only work with {@code String} objects.
 * </ul>
 * <p>
 * This issue is solved by overriding {@code encode} and {@code decode} methods from {@code JacksonEncoder} and
 * {@code JacksonDecoder} classes. When an object of {@code String} type is detected, a fallback is performed on
 * the default classes.
 * <p>
 * The proposed shared configuration is:
 * <ul>
 * <li>{@code JacksonEncoder} with overridden {@code encode} method to deal with strings
 * <li>{@code JacksonDecoder} with overridden {@code decode} method to deal with strings
 * <li>SLF4J logger enabled with Feign logging level injected as a method parameter.
 *      Logs are displayed by configuring the log level on the {@code feign} package or the {@code feign.Logger} class.
 * <li>{@code Content-Type} header with {@code application/json} default value to match {@code JacksonEncoder} and
 *     {@code JacksonDecoder} usage. This configuration works as well with the fallback for {@code String} objects.
 * </ul>
 */
public class FeignBuilder {

    private FeignBuilder() {};

    /**
     * Returns a Feign builder configured with shared configurations.
     * @param logLevel Feign logging level to configure.
     * @return A Feign builder ready to implement a client by targeting an interface describing REST endpoints.
     */
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

    /**
     * Returns a Feign builder configured with shared configurations and a {@code BasicAuthRequestInterceptor}.
     * @param logLevel Feign logging level to configure.
     * @param username Basic authentication username for authenticated REST calls.
     * @param password Basic authentication password for authenticated REST calls.
     * @return A Feign builder ready to implement a client by targeting an interface describing REST endpoints.
     */
    public static Feign.Builder createBuilder(Logger.Level logLevel, String username, String password) {
        BasicAuthRequestInterceptor requestInterceptor =
                new BasicAuthRequestInterceptor(username, password);
        return createBuilder(logLevel).requestInterceptor(requestInterceptor);
    }

}
