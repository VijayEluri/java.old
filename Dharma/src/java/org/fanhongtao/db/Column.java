package org.fanhongtao.db;

/**
 * @author Dharma
 * @created 2009-6-3
 */
public class Column
{
    /**  字段的类型 */
    public interface ColumnType
    {
        public static final int INVALID = 0;

        public static final int INTEGER = 1;

        public static final int VARCHAR = 2;

        public static final int DATE = 3;
    }

    private static final String COLUMN_TYPE[] = { "INVALID", "INTEGER", "VARCHAR", "DATE" };

    /** 字段的名字 */
    private String name;

    /** 字段的类型，取值： {@link ColumnType} */
    private int type;

    /** 是否是主键 */
    private boolean key;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setTypeStr(String typeStr)
    {
        for (int i = 0; i < COLUMN_TYPE.length; i++)
        {
            if (COLUMN_TYPE[i].equals(typeStr))
            {
                setType(i);
            }
        }
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public boolean isKey()
    {
        return key;
    }

    public void setKey(boolean key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        buf.append("Column: ");
        buf.append(name);
        buf.append('@');
        buf.append(COLUMN_TYPE[type]);
        if (isKey())
        {
            buf.append("#KEY");
        }
        return buf.toString();
    }

}
