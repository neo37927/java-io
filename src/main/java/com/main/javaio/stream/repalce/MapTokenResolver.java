package com.main.javaio.stream.repalce;

import java.util.Map;

/**
 * @author xiaolin
 * @date 2019/12/3
 **/
public class MapTokenResolver implements ITokenResolver {
    protected Map<String, String> tokenMap;

    public MapTokenResolver(Map<String, String> tokenMap) {
        this.tokenMap = tokenMap;
    }

    @Override
    public String resolveToken(String tokenName) {
        return this.tokenMap.get(tokenName);
    }
}
