package fun.easyspring.core.io;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import fun.easyspring.beans.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Create by DiaoHao on 2021/8/12 13:33
 */
public class ClassPathResource implements Resource {

    private final String classPath;

    private ClassLoader classLoader;

    public ClassPathResource(String classPath) {
        this(classPath, (ClassLoader) null);
    }

    public ClassPathResource(String classPath, ClassLoader classLoader) {
        Assert.notNull(classPath, "classpath must not be null");
        this.classPath = classPath;
        this.classLoader = classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream is = classLoader.getResourceAsStream(classPath);
        if (null == is) {
            throw new FileNotFoundException(this.classPath + " cannot be opened because it does not exist");
        }
        return is;
    }
}
