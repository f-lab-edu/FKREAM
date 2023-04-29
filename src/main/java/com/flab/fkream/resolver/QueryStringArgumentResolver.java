package com.flab.fkream.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class QueryStringArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(QueryStringArgResolver.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String queryString = request.getQueryString();
        if (queryString == null) {
            return null;
        }
        String json = qs2json(queryString);
        System.out.println("parameter.getParameterType() = " + parameter.getParameterType());
        return mapper.readValue(json, parameter.getParameterType());
    }

    private String qs2json(String a) {
        String res = "{\"";

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '=') {
                res += "\"" + ":" + "\"";
            } else if (a.charAt(i) == '&') {
                res += "\"" + "," + "\"";
            } else {
                res += a.charAt(i);
            }
        }
        res += "\"" + "}";
        return res;
    }
}
