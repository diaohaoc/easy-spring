package fun.easyspring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create by DiaoHao on 2021/8/12 13:32
 */
public interface Resource {
    InputStream getInputStream() throws IOException;
}
