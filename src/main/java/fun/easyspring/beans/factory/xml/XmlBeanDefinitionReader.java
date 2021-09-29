package fun.easyspring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.support.BeanDefinitionRegistry;
import fun.easyspring.beans.factory.config.BeanReference;
import fun.easyspring.beans.factory.support.AbstractBeanDefinitionReader;
import fun.easyspring.beans.utils.StringUtils;
import fun.easyspring.context.annotation.ClassPathBeanDefinitionScanner;
import fun.easyspring.core.io.Resource;
import fun.easyspring.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/12 14:11
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }


    @Override
    public void loadBeanDefinition(Resource resource) throws BeansException {
        try {
            InputStream is = resource.getInputStream();
            doLoadBeanDefinition(is);
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinition(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinition(resource);
        }
    }

    @Override
    public void loadBeanDefinition(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinition(resource);
    }

    @Override
    public void loadBeanDefinition(String[] locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinition(location);
        }
    }

    private void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 <context:component-scan>
        Element componentScan = root.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StringUtils.isEmpty(scanPath)) {
                throw new BeansException("The base-package attribute must not be null or empty");
            }
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements("bean");
        for (Element bean : beanList) {

            String id = bean.attributeValue("id");
            String name = bean.attributeValue("name");
            String className = bean.attributeValue("class");
            String initMethod = bean.attributeValue("init-method");
            String destroyMethod = bean.attributeValue("destroy-method");
            String scope = bean.attributeValue("scope");

            Class<?> clazz = Class.forName(className);

            // bean的名称，id > name
            String beanName = StringUtils.isEmpty(id) ? name : id;
            if (StringUtils.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);

            if (!StringUtils.isEmpty(scope)) {
                beanDefinition.setScope(scope);
            }

            // 填充属性
            List<Element> propertyList = bean.elements("property");
            for (Element property : propertyList) {
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");

                Object value = StringUtils.isEmpty(attrValue) ? new BeanReference(attrRef) : attrValue;

                PropertyValue pv = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(pv);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = scanPath.split(",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.scan(basePackages);
    }
}
