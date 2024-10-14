package edu.du.sb1010.config;

import edu.du.sb1010.aop2.EveningGreet;
import edu.du.sb1010.aop2.Greet;
import edu.du.sb1010.aop2.SampleAspect;
import edu.du.sb1010.aspect.CacheAspect;
import edu.du.sb1010.aspect.ExeTimeAspect;
import edu.du.sb1010.chap07.Calculator;
import edu.du.sb1010.chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppCtx {

    @Bean
    public ExeTimeAspect exeTimeAspect1() {
        return new ExeTimeAspect();
    }

    @Bean
    public Calculator calculator1() {
        return new RecCalculator();
    }
}
