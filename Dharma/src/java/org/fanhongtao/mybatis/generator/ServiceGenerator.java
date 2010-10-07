package org.fanhongtao.mybatis.generator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.WordUtils;
import org.fanhongtao.xml.DigesterUtils;

/**
 * @author Fan Hongtao
 * @created 2010-8-23
 */
public class ServiceGenerator extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    private JTextField outputDirText;
    
    private JTextField packageNameText;
    
    private JComboBox classNameCombo;
    
    private JTextField classDescText;
    
    private JTextArea mapperArea;
    
    private JTextArea xmlArea;
    
    private JTextArea serviceArea;
    
    private ExStringWriter mapperWritter;
    
    private ExStringWriter xmlWritter;
    
    private ExStringWriter serviceWritter;
    
    @SuppressWarnings("rawtypes")
    private Class clazz;
    
    private static Map<String, String> classMap = new LinkedHashMap<String, String>();
    
    public ServiceGenerator()
    {
        ClassInfo classInfo = new ClassInfo();
        InputStream input = classInfo.getClass().getResourceAsStream("classinfo.xml");
        URL url = classInfo.getClass().getResource("classinfo_rules.xml");
        try
        {
            classInfo = (ClassInfo) DigesterUtils.parse(classInfo, input, url);
            classMap = classInfo.getClassMap();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        setTitle("MyBatis Service Generator");
        setLayout(new BorderLayout(10, 20));
        
        JPanel inputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);
        createInputPanel(inputPanel);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        createTabbedPane(tabbedPane);
        
        packageNameText.setText(classInfo.getPgkName());
        
        setSize(getPreferredSize());
        
        // MyBatisConfig.setCfgFileName("");
    }
    
    private void createInputPanel(final JPanel parent)
    {
        parent.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 3, 3));
        parent.add(buttonPanel, BorderLayout.EAST);
        
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout());
        parent.add(innerPanel, BorderLayout.CENTER);
        
        JPanel classPanel = new JPanel();
        innerPanel.add(classPanel, BorderLayout.NORTH);
        JPanel dirPanel = new JPanel();
        innerPanel.add(dirPanel, BorderLayout.CENTER);
        
        JLabel label = new JLabel("package: ");
        classPanel.add(label);
        packageNameText = new JTextField(30);
        packageNameText.setText("org.fanhongtao.persistence");
        classPanel.add(packageNameText);
        
        label = new JLabel("class: ");
        classPanel.add(label);
        classNameCombo = new JComboBox();
        classPanel.add(classNameCombo);
        Iterator<Map.Entry<String, String>> iter = classMap.entrySet().iterator();
        while (iter.hasNext())
        {
            Map.Entry<String, String> entry = iter.next();
            classNameCombo.addItem(entry.getKey());
        }
        classNameCombo.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String desc = classMap.get(classNameCombo.getSelectedItem());
                classDescText.setText(desc);
            }
        });
        
        label = new JLabel("desc: ");
        classPanel.add(label);
        classDescText = new JTextField(10);
        classDescText.setText(classMap.get(classNameCombo.getSelectedItem()));
        classPanel.add(classDescText);
        
        label = new JLabel("Output Dir:");
        dirPanel.add(label);
        outputDirText = new JTextField(60);
        outputDirText.setText("D:\\temp\\mybatis");
        dirPanel.add(outputDirText);
        
        JButton btnChoose = new JButton("choose");
        dirPanel.add(btnChoose);
        btnChoose.setMnemonic(java.awt.event.KeyEvent.VK_C);
        btnChoose.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choose output dir");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = chooser.showOpenDialog(null);
                if (JFileChooser.APPROVE_OPTION == result)
                {
                    outputDirText.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
        
        JButton btnGenerate = new JButton("Generate");
        buttonPanel.add(btnGenerate);
        btnGenerate.setMnemonic(java.awt.event.KeyEvent.VK_G);
        btnGenerate.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String className = packageNameText.getText() + "." + (String) classNameCombo.getSelectedItem();
                try
                {
                    generate(className, true);
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        
        JButton btnGenerateAll = new JButton("Generate All");
        buttonPanel.add(btnGenerateAll);
        btnGenerateAll.setMnemonic(java.awt.event.KeyEvent.VK_A);
        btnGenerateAll.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Iterator<Map.Entry<String, String>> iter = classMap.entrySet().iterator();
                while (iter.hasNext())
                {
                    Map.Entry<String, String> entry = iter.next();
                    classNameCombo.setSelectedItem(entry.getKey());
                    String className = packageNameText.getText() + "." + (String) classNameCombo.getSelectedItem();
                    try
                    {
                        generate(className, false);
                    }
                    catch (Exception e1)
                    {
                        e1.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(parent, "Finished", "notice", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    private void createTabbedPane(JTabbedPane tabbedPane)
    {
        mapperArea = new JTextArea(20, 90);
        mapperArea.setEditable(false);
        tabbedPane.addTab("Mapper", new JScrollPane(mapperArea));
        
        xmlArea = new JTextArea(20, 90);
        xmlArea.setEditable(false);
        tabbedPane.addTab("XML", new JScrollPane(xmlArea));
        
        serviceArea = new JTextArea(20, 90);
        serviceArea.setEditable(false);
        tabbedPane.addTab("Service", new JScrollPane(serviceArea));
    }
    
    private void generate(String className, boolean showInScreen)
        throws Exception
    {
        clazz = Class.forName(className);
        Table table = new TableUtils(clazz).parseTable();
        
        mapperWritter = new ExStringWriter(1024);
        xmlWritter = new ExStringWriter(1024);
        serviceWritter = new ExStringWriter(1024);
        
        generateHead(table);
        generateDeleteMethod(table);
        generateInsertMethod(table);
        generateQueryMethod(table);
        generateUpdateMethod(table);
        generateTail(table);
        
        if (showInScreen)
        {
            mapperArea.setText(mapperWritter.toString());
            xmlArea.setText(xmlWritter.toString());
            serviceArea.setText(serviceWritter.toString());
        }
        
        String dir = outputDirText.getText();
        writeFile(dir + "/iface", clazz.getSimpleName() + "Mapper.java", mapperWritter.toString());
        writeFile(dir + "/mapper", clazz.getSimpleName() + "Mapper.xml", xmlWritter.toString());
        writeFile(dir + "/service", clazz.getSimpleName() + "Service.java", serviceWritter.toString());
    }
    
    private void writeFile(String dirName, String fileName, String content)
        throws IOException
    {
        File dir = new File(dirName);
        if (!dir.exists())
        {
            try
            {
                dir.mkdirs();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Failed to create dir [" + dirName + "]", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        
        BufferedWriter writter = null;
        try
        {
            writter = new BufferedWriter(new FileWriter(dirName + "/" + fileName, false));
            writter.write(content);
        }
        finally
        {
            if (null != writter)
            {
                writter.close();
            }
        }
    }
    
    private void generateHead(Table table)
    {
        String pkgName = packageNameText.getText();
        String mapperName = clazz.getSimpleName() + "Mapper";
        String serviceName = clazz.getSimpleName() + "Service";
        String mapperClass = pkgName + ".iface." + mapperName;
        String author = System.getenv("USERNAME");
        String dateStr = formatter.format(new Date());
        
        xmlWritter.writeln("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xmlWritter.writeln("<!DOCTYPE mapper");
        xmlWritter.writeln("    PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
        xmlWritter.writeln("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> ");
        xmlWritter.writeln("");
        xmlWritter.writeln("<mapper namespace=\"" + mapperClass + "\">");
        
        mapperWritter.writeln("package " + pkgName + ".iface");
        mapperWritter.writeln("");
        mapperWritter.writeln("import java.util.List;");
        mapperWritter.writeln("");
        mapperWritter.writeln("import org.apache.ibatis.session.RowBounds;");
        mapperWritter.writeln("");
        mapperWritter.writeln("import " + clazz.getName() + ";");
        mapperWritter.writeln("");
        mapperWritter.writeln("/**");
        mapperWritter.writeln(" * @author " + author);
        mapperWritter.writeln(" * @created " + dateStr);
        mapperWritter.writeln(" */");
        mapperWritter.writeln("public interface " + mapperName + " extends BasicMapper<" + clazz.getSimpleName() + ">");
        
        serviceWritter.writeln("package " + pkgName + ".service");
        serviceWritter.writeln("");
        serviceWritter.writeln("import java.util.List;");
        serviceWritter.writeln("");
        serviceWritter.writeln("import org.apache.ibatis.session.RowBounds;");
        serviceWritter.writeln("import org.apache.ibatis.session.SqlSession;");
        serviceWritter.writeln("import " + mapperClass + ";");
        serviceWritter.writeln("import " + clazz.getName() + ";");
        serviceWritter.writeln("");
        serviceWritter.writeln("/**");
        serviceWritter.writeln(" * @author " + author);
        serviceWritter.writeln(" * @created " + dateStr);
        serviceWritter.writeln(" */");
        serviceWritter.writeln("final class " + serviceName + " extends BaseService<" + clazz.getSimpleName() + ", "
                + mapperName + "> impements " + mapperName);
        serviceWritter.writeln("{");
        serviceWritter.writeln("    public " + serviceName + "()");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
        serviceWritter.writeln("    public " + serviceName + "(SqlSession session)");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        super(session);");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
        serviceWritter.writeln("    public " + mapperName + " getMapper()");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        return getSession().getMapper(" + mapperName + ".class);");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
    }
    
    private void generateDeleteMethod(Table table)
        throws Exception
    {
        for (Index index : table.getIndexList())
        {
            if (index.isUnique())
            {
                generateDeleteMethod(table, index);
            }
        }
    }
    
    private void generateDeleteMethod(Table table, Index index)
        throws Exception, Exception
    {
        String type;
        String param;
        String methodName;
        List<String> columnList = index.getColumnList();
        if (columnList.size() == 1) // 单字段的索引（或主键）
        {
            Field field = clazz.getDeclaredField(columnList.get(0));
            type = field.getType().getSimpleName();
            param = field.getName();
        }
        else
        {
            type = clazz.getSimpleName();
            param = WordUtils.uncapitalize(type);
        }
        methodName = "deleteBy";
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                methodName += "And";
            }
            methodName += WordUtils.capitalize(column);
        }
        
        xmlWritter.writeln("    <delete id=\"" + methodName + "\" parameterType=\"" + type + "\">");
        xmlWritter.write("        delete from " + table.getName() + " where ");
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                xmlWritter.write(" and ");
            }
            xmlWritter.write(column + " = #{" + column + "}");
        }
        xmlWritter.writeln("");
        xmlWritter.writeln("    </delete>");
        xmlWritter.writeln("    ");
        
        mapperWritter.writeln("    /**");
        mapperWritter.writeln("     * @param " + param + " xxx");
        mapperWritter.writeln("     * @return 删除的记录数");
        mapperWritter.writeln("     */");
        mapperWritter.writeln("    public int " + methodName + "(" + type + " " + param + ");");
        mapperWritter.writeln("    ");
        
        serviceWritter.writeln("    /**");
        serviceWritter.writeln("     * @param " + param + " xxx");
        serviceWritter.writeln("     * @return 删除的记录数");
        serviceWritter.writeln("     */");
        serviceWritter.writeln("    public int " + methodName + "(" + type + " " + param + ")");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        return getMapper()." + methodName + "(" + param + ")");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
    }
    
    /**
     * public int insert(Object obj); 
     * @param table
     */
    private void generateInsertMethod(Table table)
    {
        String type = clazz.getSimpleName();
        String param = WordUtils.uncapitalize(type);
        String methodName = "insert";
        
        StringBuilder sbColumn = new StringBuilder(1024);
        StringBuilder sbValue = new StringBuilder(1024);
        List<Column> columnList = table.getColumnList();
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            if (i != 0)
            {
                sbColumn.append(", ");
                sbValue.append(", ");
            }
            Column column = columnList.get(i);
            sbColumn.append(column.getName());
            sbValue.append("#{" + column.getName() + ",jdbcType=" + column.getType() + "}");
        }
        
        xmlWritter.writeln("    <insert id=\"" + methodName + "\" parameterType=\"" + type + "\">");
        xmlWritter.writeln("        insert into " + table.getName() + "(" + sbColumn.toString() + ") values ("
                + sbValue + ")");
        xmlWritter.writeln("    </insert>");
        xmlWritter.writeln("    ");
        
        mapperWritter.writeln("    /**");
        mapperWritter.writeln("     * @param " + param + " xxx");
        mapperWritter.writeln("     * @return 增加的记录数");
        mapperWritter.writeln("     */");
        mapperWritter.writeln("    public int " + methodName + "(" + type + " " + param + ");");
        mapperWritter.writeln("    ");
        
        serviceWritter.writeln("    /**");
        serviceWritter.writeln("     * @param " + param + " xxx");
        serviceWritter.writeln("     * @return 增加的记录数");
        serviceWritter.writeln("     */");
        serviceWritter.writeln("    public int " + methodName + "(" + type + " " + param + ")");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        return getMapper()." + methodName + "(" + param + ")");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
    }
    
    private void generateQueryMethod(Table table)
        throws Exception
    {
        for (Index index : table.getIndexList())
        {
            if (index.isUnique())
            {
                generateQueryMethod(table, index);
            }
        }
    }
    
    private void generateQueryMethod(Table table, Index index)
        throws Exception
    {
        String type;
        String param;
        String methodName;
        String resultType = clazz.getSimpleName();
        String returnDesc;
        if (index.isUnique())
        {
            returnDesc = "返回" + classDescText.getText() + "信息, null表示数据库中无对应的记录";
        }
        else
        {
            returnDesc = "返回" + classDescText.getText() + "信息列表, List的Size为0表示数据库中无对应的记录";
        }
        List<String> columnList = index.getColumnList();
        if (columnList.size() == 1) // 单字段的索引（或主键）
        {
            Field field = clazz.getDeclaredField(columnList.get(0));
            type = field.getType().getSimpleName();
            param = field.getName();
        }
        else
        {
            type = clazz.getSimpleName();
            param = WordUtils.uncapitalize(type);
        }
        methodName = "queryBy";
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                methodName += "And";
            }
            methodName += WordUtils.capitalize(column);
        }
        
        xmlWritter.writeln("    <select id=\"" + methodName + "\" parameterType=\"" + type + "\" resultType=\">"
                + resultType + "\"");
        xmlWritter.write("        select * from " + table.getName() + " where ");
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                xmlWritter.write(" and ");
            }
            xmlWritter.write(column + " = #{" + column + "}");
        }
        xmlWritter.writeln("");
        xmlWritter.writeln("    </select>");
        xmlWritter.writeln("    ");
        
        mapperWritter.writeln("    /**");
        mapperWritter.writeln("     * @param " + param + " xxx");
        mapperWritter.writeln("     * @return " + returnDesc);
        mapperWritter.writeln("     */");
        mapperWritter.writeln("    public " + resultType + " " + methodName + "(" + type + " " + param + ");");
        mapperWritter.writeln("    ");
        
        serviceWritter.writeln("    /**");
        serviceWritter.writeln("     * @param " + param + " xxx");
        serviceWritter.writeln("     * @return " + returnDesc);
        serviceWritter.writeln("     */");
        serviceWritter.writeln("    public " + resultType + " " + methodName + "(" + type + " " + param + ")");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        return getMapper()." + methodName + "(" + param + ")");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
        
        // 非唯一索引，还需要提供指定rowBounds的查询方法
        if (!index.isUnique())
        {
            mapperWritter.writeln("    /**");
            mapperWritter.writeln("     * @param " + param + " xxx");
            mapperWritter.writeln("     * @param rowBounds 所要查询的范围");
            mapperWritter.writeln("     * @return " + returnDesc);
            mapperWritter.writeln("     */");
            mapperWritter.writeln("    public " + resultType + " " + methodName + "(" + type + " " + param
                    + ", RowBounds rowBounds);");
            mapperWritter.writeln("    ");
            
            serviceWritter.writeln("    /**");
            serviceWritter.writeln("     * @param " + param + " xxx");
            serviceWritter.writeln("     * @param rowBounds 所要查询的范围");
            serviceWritter.writeln("     * @return " + returnDesc);
            serviceWritter.writeln("     */");
            serviceWritter.writeln("    public " + resultType + " " + methodName + "(" + type + " " + param
                    + ", RowBounds rowBounds)");
            serviceWritter.writeln("    {");
            serviceWritter.writeln("        return getMapper()." + methodName + "(" + param + ", rowBounds)");
            serviceWritter.writeln("    }");
            serviceWritter.writeln("    ");
        }
    }
    
    private void generateUpdateMethod(Table table)
        throws Exception
    {
        for (Index index : table.getIndexList())
        {
            if (index.isUnique())
            {
                generateUpdateMethod(table, index);
            }
        }
    }
    
    private void generateUpdateMethod(Table table, Index index)
        throws Exception
    {
        String type;
        String param;
        String methodName;
        String returnDesc = "修改的记录数";
        List<String> columnList = index.getColumnList();
        if (columnList.size() == 1) // 单字段的索引（或主键）
        {
            Field field = clazz.getDeclaredField(columnList.get(0));
            type = field.getType().getSimpleName();
            param = field.getName();
        }
        else
        {
            type = clazz.getSimpleName();
            param = WordUtils.uncapitalize(type);
        }
        methodName = "updateBy";
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                methodName += "And";
            }
            methodName += WordUtils.capitalize(column);
        }
        
        xmlWritter.writeln("    <delete id=\"" + methodName + "\" parameterType=\"" + type + "\"");
        xmlWritter.write("        delete from " + table.getName() + " where ");
        for (int i = 0, n = columnList.size(); i < n; i++)
        {
            String column = columnList.get(i);
            if (i != 0)
            {
                xmlWritter.write(" and ");
            }
            xmlWritter.write(column + " = #{" + column + "}");
        }
        xmlWritter.writeln("");
        xmlWritter.writeln("    </select>");
        xmlWritter.writeln("    ");
        
        mapperWritter.writeln("    /**");
        mapperWritter.writeln("     * @param " + param + " xxx");
        mapperWritter.writeln("     * @return " + returnDesc);
        mapperWritter.writeln("     */");
        mapperWritter.writeln("    public int " + methodName + "(" + type + " " + param + ");");
        mapperWritter.writeln("    ");
        
        serviceWritter.writeln("    /**");
        serviceWritter.writeln("     * @param " + param + " xxx");
        serviceWritter.writeln("     * @return " + returnDesc);
        serviceWritter.writeln("     */");
        serviceWritter.writeln("    public int " + methodName + "(" + type + " " + param + ")");
        serviceWritter.writeln("    {");
        serviceWritter.writeln("        return getMapper()." + methodName + "(" + param + ")");
        serviceWritter.writeln("    }");
        serviceWritter.writeln("    ");
    }
    
    private void generateTail(Table table)
    {
        xmlWritter.writeln("</mapper>");
        mapperWritter.writeln("}");
        serviceWritter.writeln("}");
    }
    
    public static void main(String[] args)
    {
        ServiceGenerator frame = new ServiceGenerator();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
