package org.fanhongtao.tools.dbviewer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Dharma
 * @created 2010-7-7
 */
public abstract class AbstractIndexTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = 1L;

    private String[] columnNames = null;

    private List<String[]> valueList = new ArrayList<String[]>();

    /**
     * 
     */
    public AbstractIndexTableModel(ResultSet rs, String[] columnNames)
    {
        super();
        this.columnNames = columnNames;
        if (null == rs)
        {
            return;
        }

        try
        {
            while (rs.next())
            {
                String[] values = new String[columnNames.length];
                for (int i = 0; i < columnNames.length; i++)
                {
                    values[i] = rs.getString(columnNames[i]);
                }
                valueList.add(values);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public int getRowCount()
    {
        return valueList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return valueList.get(rowIndex)[columnIndex];
    }

}
