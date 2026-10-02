package ltd.newbee.mall.monomorph.dto.generated.client;

import ltd.newbee.mall.monomorph.dto.generated.proto.tokentomallusermethodargumentresolver.TokenToMallUserMethodArgumentResolverDTO;
import ltd.newbee.mall.monomorph.id.generated.client.NewBeeMallUserTokenMapper;
import ltd.newbee.mall.monomorph.id.generated.helpers.IDMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Auto-generated DTO gRPC client for {@link TokenToMallUserMethodArgumentResolver}.
 *
 * <p>This class is also the Spring {@link HandlerMethodArgumentResolver} bean that
 * {@code NeeBeeMallWebMvcConfigurer} registers.  It delegates the actual argument
 * resolution to the original
 * {@code ltd.newbee.mall.config.handler.TokenToMallUserMethodArgumentResolver} so the
 * token handling logic stays in one place.</p>
 */
@Component("monomorphTokenToMallUserMethodArgumentResolver")
public class TokenToMallUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private TokenToMallUserMethodArgumentResolverDTO dtoInstance;

    private TokenToMallUserMethodArgumentResolver(TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        this.dtoInstance = dtoInstance;
    }

    public TokenToMallUserMethodArgumentResolver() {
        this(TokenToMallUserMethodArgumentResolverDTO.getDefaultInstance());
    }

    public TokenToMallUserMethodArgumentResolverDTO toDTO() {
        return this.dtoInstance;
    }

    public static TokenToMallUserMethodArgumentResolver fromDTO(
            TokenToMallUserMethodArgumentResolverDTO dtoInstance) {
        return new TokenToMallUserMethodArgumentResolver(dtoInstance);
    }

    public MallUserMapper getMallUserMapper() {
        return MallUserMapper.fromDTO(dtoInstance.getMallUserMapper());
    }

    public void setMallUserMapper(MallUserMapper mallUserMapper) {
        this.dtoInstance = this.dtoInstance.toBuilder()
                .setMallUserMapper(mallUserMapper.toDTO())
                .build();
    }

    public NewBeeMallUserTokenMapper getNewBeeMallUserTokenMapper() {
        try {
            return (NewBeeMallUserTokenMapper) IDMapper.fromID(
                    dtoInstance.getNewBeeMallUserTokenMapper());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setNewBeeMallUserTokenMapper(NewBeeMallUserTokenMapper newBeeMallUserTokenMapper) {
        try {
            this.dtoInstance = this.dtoInstance.toBuilder()
                    .setNewBeeMallUserTokenMapper(IDMapper.toID(newBeeMallUserTokenMapper))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private ltd.newbee.mall.config.handler.TokenToMallUserMethodArgumentResolver delegate;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return delegate.supportsParameter(parameter);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return delegate.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}

