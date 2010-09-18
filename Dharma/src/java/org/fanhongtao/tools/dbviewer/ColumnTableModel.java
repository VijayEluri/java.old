package org.fanhongtao.tools.dbviewer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Dharma
 * @created 2010-7-4
 */
public class ColumnTableModel extends AbstractTableModel
{

    private static final long serialVersionUID = 1L;

    private static final String[] columnNames = { "Index", "Column", "Type", "Precision", "Scale" };

    private List<ColumnInfo> valueList = new ArrayList<ColumnInfo>();

    public ColumnTableModel(ResultSet rs)
    {
        if (null == rs)
        {
            return;
        }
        try
        {
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int numColumns = resultSetMetaData.getColumnCount();
            for (int i = 1; i < (numColumns + 1); i++)
            {
                String name = resultSetMetaData.getColumnName(i);
                String typeName = resultSetMetaData.getColumnTypeName(i);
                int precision = resultSetMetaData.getPrecision(i);
                int scale = resultSetMetaData.getScale(i);
                ColumnInfo info = new ColumnInfo(i, name, typeName, precision, scale);
                valueList.add(info);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount()
    {
        return valueList.size();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ColumnInfo info = valueList.get(rowIndex);
        switch (columnIndex)
        {
        case 0:
            return info.getIndex();
        case 1:
            return info.getName();
        case 2:
            return info.getType();
        case 3:
            return info.getPrecision();
        case 4:
            return info.getScale();
        }
        return null;
    }

}

class ColumnInfo
{
    private int index;

    private String name;

    private String type;

    private int precision;

    private int scale;

    public ColumnInfo(int index, String name, String type, int precision, int scale)
    {
        this.index = index;
        this.name = name;
        this.type = type;
        this.precision = precision;
        this.scale = scale;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public int getPrecision()
    {
        return precision;
    }

    public void setPrecision(int precision)
    {
        this.precision = precision;
    }

    public int getScale()
    {
        return scale;
    }

    public void setScale(int scale)
    {
        this.scale = scale;
    }

}
