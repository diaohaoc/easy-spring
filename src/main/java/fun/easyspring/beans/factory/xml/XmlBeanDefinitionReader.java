package fun.easyspring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.support.BeanDefinitionRegistry;
import fun.easyspring.beans.factory.config.BeanReference;
import fun.easyspring.beans.factory.support.AbstractBeanDefinitionReader;
import fun.easyspring.beans.utils.StringUtils;
import fun.easyspring.core.io.Resource;
import fun.easyspring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

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
        } catch (IOException | ClassNotFoundException e) {
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

    private void doLoadBeanDefinition(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }

            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethod = bean.getAttribute("init-method");
            String destroyMethod = bean.getAttribute("destroy-method");
            String scope = bean.getAttribute("scope");

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
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }

                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) {
                    continue;
                }

                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

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
}
