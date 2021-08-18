package fun.easyspring.context.support;

import fun.easyspring.beans.factory.support.DefaultListableBeanFactory;
import fun.easyspring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * Create by DiaoHao on 2021/8/13 14:34
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] locations = getConfigurations();
        if (null != locations) {
            reader.loadBeanDefinition(locations);
        }
    }

    protected abstract String[] getConfigurations();
}
