package fun.easyspring.beans.factory.config;

/**
 * 用于表示 bean 所依赖的 bean
 *
 * Create by DiaoHao on 2021/8/11 22:08
 */
public class BeanReference {

    private String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setName(String beanName) {
        this.beanName = beanName;
    }
}
