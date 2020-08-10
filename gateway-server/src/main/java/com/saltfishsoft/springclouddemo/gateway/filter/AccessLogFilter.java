package com.saltfishsoft.springclouddemo.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.saltfishsoft.springclouddemo.gateway.model.AccessLog;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/3/21.
 */
@Slf4j
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {
    //private String[] skipUrls = {"/ljl-auth/oauth/token"};
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI requestUri = request.getURI();
        //不记录静态资源的请求
        if(requestUri.getPath().contains("/webjars")
                || requestUri.getPath().contains("/js")
                || requestUri.getPath().contains("/css")
                || requestUri.getPath().contains("/img")
                || requestUri.getPath().contains("/image")){
            return chain.filter(exchange);
        }
        //只记录 http 请求(包含 https)
        String schema = requestUri.getScheme();
        if ((!"http".equals(schema) && !"https".equals(schema))){
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("Authorization");
        //没有accessToken返回未授权错误
//        if (token == null || token.isEmpty()) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }

        AccessLog accessLog = new AccessLog();
        accessLog.setAccessTime(this.getAccessTime());
        accessLog.setPath(requestUri.getPath());
        accessLog.setQueryString(request.getQueryParams());
        accessLog.setToken(token==null ? "" :token);
        exchange.getAttributes().put("startTime", System.currentTimeMillis());
        String method = request.getMethodValue();
        String contentType = request.getHeaders().getFirst("Content-Type");
        //此处要排除流文件类型,比如上传的文件
        if (("POST".equalsIgnoreCase(method) || "PATCH".equalsIgnoreCase(method)) && !contentType.startsWith("multipart/form-data")){
            String bodyStr = exchange.getAttribute("cachedRequestBodyObject");
            accessLog.setBody(formatStr(bodyStr));
            return returnMono(chain, exchange, accessLog);
        }else{
            return returnMono(chain, exchange, accessLog);
        }
    }

    private Mono<Void> returnMono(GatewayFilterChain chain,ServerWebExchange exchange, AccessLog log){
        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            Long startTime = exchange.getAttribute("startTime");
            if (startTime != null){
                long executeTime = (System.currentTimeMillis() - startTime);
                log.setExpendTime(executeTime);
                log.setHttpCode(Objects.requireNonNull(exchange.getResponse().getStatusCode()).value());
                writeAccessLog(JSON.toJSONString(log) + "\r\n");
            }
        }));
    }

    private void writeAccessLog(String str){
        File file = new File("access.log");
        if (!file.exists()){
            try {
                if (file.createNewFile()){
                    file.setWritable(true);
                }
            } catch (IOException e) {
                log.error("创建访问日志文件失败.{}",e.getMessage(),e);
            }
        }

        try(FileWriter fileWriter = new FileWriter(file.getName(),true)){
            fileWriter.write(str);
        } catch (IOException e) {
            log.error("写访问日志到文件失败. {}", e.getMessage(),e);
        }

    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 获取请求体中的字符串内容
     * @param serverHttpRequest
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest){
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return sb.toString();
    }


    private String formatStr(String str){
        if (str != null && str.length() > 0) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }

    private DataBuffer stringBuffer(String value){
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    private String getAccessTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
