package com.main.javaio.stream.repalce;

/**
 * token解析器
 *
 * @author xiaolin
 * @date 2019/12/3
 **/
public interface ITokenResolver {
    /**
     * 解析token
     *
     * @param tokenName
     * @return
     */
    String resolveToken(String tokenName);
}
