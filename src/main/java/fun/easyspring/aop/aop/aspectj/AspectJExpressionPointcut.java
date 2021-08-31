package fun.easyspring.aop.aop.aspectj;

import fun.easyspring.aop.aop.ClassFilter;
import fun.easyspring.aop.aop.MethodMatcher;
import fun.easyspring.aop.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * Create by DiaoHao on 2021/8/26 9:47
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private static final Set<PointcutPrimitive> SUPPORT_PRIMITIVES = new HashSet<>();

    private transient PointcutExpression pointcutExpression;


    static {
        SUPPORT_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    public AspectJExpressionPointcut(String expression) {
        PointcutParser parser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORT_PRIMITIVES, this.getClass().getClassLoader());
        this.pointcutExpression = parser.parsePointcutExpression(expression);

    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> clazz) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
