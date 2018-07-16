import org.smart4j.framework.HelpLoader;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;

public class Test {

    public void test() {
        System.out.println("test");
    }

    public static void main(String[] args) throws NoSuchMethodException {
        /*Test test = new Test();
        Method method = Test.class.getDeclaredMethod("test");
        Param param = new Param(new HashMap<String, Object>());
        ReflectionUtil.invokeMethod(test, method, param);*/
        HelpLoader.init();
    }
}
