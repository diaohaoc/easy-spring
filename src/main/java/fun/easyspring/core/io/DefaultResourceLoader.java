package fun.easyspring.core.io;

import cn.hutool.core.lang.Assert;

/**
 * Create by DiaoHao on 2021/8/12 13:55
 */
public class DefaultResourceLoader implements ResourceLoader {

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        if (location.startsWith(CLASSPATH_UTL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_UTL_PREFIX.length()));
        } else {
            // todo
            // 其他类型的资源加载
        }
        return null;
    }
}
