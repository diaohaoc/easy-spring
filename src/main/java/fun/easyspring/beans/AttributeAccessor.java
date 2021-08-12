package fun.easyspring.beans;

/**
 * 对任意 bean 实例的属性的添加和访问的接口
 */
public interface AttributeAccessor {

    /**
     * 添加新的属性
     *
     * @param name  属性名称
     * @param value 属性的值
     */
    void setAttribute(String name, Object value);

    /**
     * 获取指定属性的值
     *
     * @param name 属性名称
     * @return 属性的值
     */
    Object getAttribute(String name);

    /**
     * 删除指定的属性
     *
     * @param name 属性的名称
     * @return 被删除的属性的值
     */
    Object removeAttribute(String name);

    /**
     * 判断是否存在该属性
     *
     * @param name 属性名称
     * @return 如果存在该属性则返回 true
     */
    boolean hasAttribute(String name);

    /**
     * 返回所有的属性名称
     *
     * @return 属性名称的数组
     */
    String[] attributeNames();


}
