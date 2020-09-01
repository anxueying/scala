package com.atguigu.hive.udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shelly An
 * @create 2020/8/15 11:42
 */
public class ExplodeJSONArray extends GenericUDTF {
    private String[] result = new String[1];
    /**
     * @param argOIs 告诉hive函数传入的参数类型
     * @return 返回函数生成的每一行内容的ObjectInspector
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //获取传入的所有相关字段
        List<? extends StructField> inputFields = argOIs.getAllStructFieldRefs();
        //检查参数个数是否是单个
        if (inputFields.size()!=1) {
            throw new UDFArgumentException("只能传入单个String类型的参数！");
        }
        //检查传入的参数是否是String类型
        if (!"string".equals(inputFields.get(0).getFieldObjectInspector().getTypeName())) {
            throw new UDFArgumentException("只能传入String类型的参数！");
        }
        //返回生成的一行内容（String）的类型
        //返回的字段名
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("action");
        //返回的字段类型
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,fieldOIs);
    }

    /**
     * 生成返回的数据
     * @param args 传入的数据
     * @throws HiveException
     */
    @Override
    public void process(Object[] args) throws HiveException {
        //转成json对象
        JSONArray jsonArray = new JSONArray(args[0].toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            String s = jsonArray.getString(i);
            result[0]=s;
            forward(result);
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
