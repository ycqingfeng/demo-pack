import cn.ycsin.engine.tag.api.entity.ITag;
import cn.ycsin.engine.tag.core.DynamicLoader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

public class DynamicLoaderTestCase {

    private String javaSrc = "public class TestClass{" +
            "public void sayHello(String msg) {" +
            "System.out.printf(\"Hello %s! This message from a Java String.%n\",msg);" +
            "}" +
            "public int add(int a,int b){" +
            "return a+b;" +
            "}" +
            "}";

    @Test
    public void testCompile() {

    }

    @Test
    public void testInvoke() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
        File file = new File("I:\\dev_java2019\\demos\\spring_cloud\\engine.tag\\engine.tag.core\\src\\test\\java\\res\\TestTagImpl.java");
        String content = FileUtils.readFileToString(file);
        Map<String, byte[]> bytecode = DynamicLoader.compile("TestTagImpl.java", content);
        DynamicLoader.MemoryClassLoader classLoader = new DynamicLoader.MemoryClassLoader(bytecode);
        Class clazz = classLoader.loadClass("TestTagImpl");

        boolean isSubClass = false;
        Class[] interfaces = clazz.getInterfaces();
        for (Class ifaceClass :  interfaces){
            if (ifaceClass.equals(ITag.class)){
                isSubClass = true;
                break;
            }
        }

        if( isSubClass) {
            ITag object = (ITag) clazz.newInstance();
//            Method method = clazz.getMethod("sayHello");
//            Object returnValue = method.invoke(object);
//            System.out.println(returnValue);
            object.sayHello();
        }
    }
}
