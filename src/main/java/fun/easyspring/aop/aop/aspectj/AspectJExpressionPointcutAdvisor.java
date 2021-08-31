package fun.easyspring.aop.aop.aspectj;

import fun.easyspring.aop.aop.ClassFilter;
import fun.easyspring.aop.aop.MethodMatcher;
import fun.easyspring.aop.aop.Pointcut;
import fun.easyspring.aop.aop.PointcutAdvisor;
import fun.easyspring.aop.aopalliance.aop.Advice;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by DiaoHao on 2021/8/26 9:47
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // 切面
    private AspectJExpressionPointcut pointcut;

    private Advice advice;

    private String expression;


    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            this.pointcut = new AspectJExpressionPointcut(expression);
        }
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
