package fun.easyspring.core.io;

/**
 * Create by DiaoHao on 2021/8/12 13:54
 */
public interface ResourceLoader {
    String CLASSPATH_UTL_PREFIX = "classpath:";

    Resource getResource(String location);

}
