package test.registertest;

import fun.easyspring.aop.aop.TargetSource;
import fun.easyspring.aop.aop.aspectj.AspectJExpressionPointcut;
import fun.easyspring.aop.aop.framework.AdvisedSupport;
import fun.easyspring.aop.aop.framework.CglibAopProxy;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.PropertyValues;
import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanReference;
import fun.easyspring.beans.factory.support.DefaultListableBeanFactory;
import fun.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import fun.easyspring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import test.bean.AnotherService;
import test.bean.IUserService;
import test.bean.UserDao;
import test.bean.UserService;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/7/17 22:28
 */
public class ApiTest {

    @Test
    public void test_api() {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);

        reader.loadBeanDefinition("classpath:easy-spring.xml");

        UserService userService = (UserService) beanFactory.getBean("userService");
//        userService.queryUserInfo();
    }

    @Test
    public void test_ac() {

        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:easy-spring.xml");
        beanFactory.registerShutdownHook();

        UserService userService1 = (UserService) beanFactory.getBean("userService");
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        System.out.println(userService1);
        System.out.println(userService2);

    }

    @Test
    public void test_circle_dependent() {

        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:easy-spring.xml");
        beanFactory.registerShutdownHook();

//        UserService userService = (UserService) beanFactory.getBean("userService");
        AnotherService anotherService = (AnotherService) beanFactory.getBean("anotherService");

//        System.out.println(userService.getAnotherService());
        System.out.println(anotherService.getUserService());

    }

    @Test
    public void test_aop() throws NoSuchMethodException {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:easy-spring.xml");
        IUserService userService = (IUserService) beanFactory.getBean("userService");

//        userService.queryUserInfo();

    }

    @Test
    public void test_cglib() throws NoSuchMethodException {
        IUserService userService = new UserService();
        AdvisedSupport config = new AdvisedSupport();
        config.setTargetSource(new TargetSource(userService));
        CglibAopProxy proxy = new CglibAopProxy(config);
        IUserService proxy1 = (IUserService) proxy.getProxy();
        AdvisedSupport config1 = new AdvisedSupport();
        config1.setTargetSource(new TargetSource(proxy1));
        CglibAopProxy proxy2 = new CglibAopProxy(config1);
        IUserService userService1 = (IUserService) proxy2.getProxy();
        int i = 0;
    }

    @Test
    public void test_annotation() throws NoSuchMethodException {
        ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext("classpath:easy-spring.xml");
        IUserService userService = (IUserService) beanFactory.getBean("userService");
        userService.queryUserInfo("10001");
    }

}
