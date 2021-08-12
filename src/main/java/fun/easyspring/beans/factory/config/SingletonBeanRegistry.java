package fun.easyspring.beans.factory.config;

public interface SingletonBeanRegistry {

    /**
     * 获取一个单例 bean
     *
     * @param beanName bean 的名称
     * @return 单例 bean
     */
    Object getSingleton(String beanName);

    /**
     * 注册单例 bean
     *
     * @param beanName bean 的名称
     * @param singletonObject 注册的 bean
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 判断指定的 bean 是否存在
     *
     * @param beanName bean 的名称
     * @return 如果存在，则返回 true
     */
    boolean containsSingleton(String beanName);

    /**
     * 获取容器中所有的单例 bean 的名称
     *
     * @return bean 的名称数组
     */
    String[] getSingletonNames();

}
