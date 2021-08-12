package test.registertest;

import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.PropertyValues;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanReference;
import fun.easyspring.beans.factory.support.DefaultListableBeanFactory;
import fun.easyspring.beans.factory.xml.XmlBeanDefinitionReader;
import org.junit.Test;
import test.bean.UserDao;
import test.bean.UserService;

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
        userService.queryUserInfo();
    }

}
