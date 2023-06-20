package com.meta.laundry_day.common.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Aspect
@Component
public class LogControllerAndService {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final Logger log = LoggerFactory.getLogger("CS_LOGGER");
    private ObjectMapper mapper = new ObjectMapper();


    private String host = "";
    private String ip = "";
    private String clientIp = "";
    private String clientUrl = "";

    @PostConstruct
    public void init() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        this.host = addr.getHostName();
        this.ip = addr.getHostAddress();
    }

    @Around("bean(*Controller)")
    public Object controllerAroundLogging(ProceedingJoinPoint pjp) throws Throwable {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        this.clientIp = request.getRemoteAddr();
        this.clientUrl = request.getRequestURL().toString();
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        ResponseData logelk = new ResponseData();
        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("CONTROLLER_REQ");
        logelk.setParameter(mapper.writeValueAsString(request.getParameterMap()));
        log.info("{}", mapper.writeValueAsString(logelk));

        Object result = pjp.proceed();

        timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));

        logelk.setTimestamp(timeStamp);
        logelk.setType("CONTROLLER_RES");
        logelk.setParameter(mapper.writeValueAsString(result));
        log.info("{}", mapper.writeValueAsString(logelk));

        return result;
    }

    @Before("bean(*Service)")
    public void serviceBeforeLogging(JoinPoint pjp) throws Throwable {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        Object[] argNames = pjp.getArgs();

        ResponseData logelk = new ResponseData();
        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_REQ");

        String param = "";

        try {
            logelk.setParameter(mapper.writeValueAsString(argNames));
        } catch (Exception e1) {
            for (Object a : argNames) {
                try {
                    param += mapper.writeValueAsString(a);
                } catch (Exception e2) {
                    param += mapper.writeValueAsString(String.valueOf(a));
                }
            }
            logelk.setParameter(mapper.writeValueAsString(param));
        }
        log.info("{}", mapper.writeValueAsString(logelk));
    }

    @AfterReturning(pointcut = "bean(*Service)", returning = "retVal")
    public void serviceAfterReturningLogging(JoinPoint pjp, Object retVal) throws Throwable {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        ResponseData logelk = new ResponseData();
        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_RES");
        logelk.setParameter(mapper.writeValueAsString(String.valueOf(retVal)));
        log.info("{}", mapper.writeValueAsString(logelk));
    }

    @AfterThrowing(pointcut = "bean(*Service)", throwing = "exception")
    public void afterThrowingTargetMethod(JoinPoint pjp, Exception exception) throws Exception {
        String timeStamp = new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Timestamp(System.currentTimeMillis()));
        String callFunction = pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName();

        ResponseData logelk = new ResponseData();
        logelk.setTimestamp(timeStamp);
        logelk.setHostname(host);
        logelk.setHostIp(ip);
        logelk.setClientIp(clientIp);
        logelk.setClientUrl(clientUrl);
        logelk.setCallFunction(callFunction);
        logelk.setType("SERVICE_RES");
        logelk.setParameter(mapper.writeValueAsString(String.valueOf(exception)));
        log.info("{}", mapper.writeValueAsString(logelk));
    }
}