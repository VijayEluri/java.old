package org.fanhongtao.tools.dbviewer;

import java.sql.ResultSet;

/**
 * @author Dharma
 * @created 2010-7-6
 */
public class IndexTableModel extends AbstractIndexTableModel
{
    private static final long serialVersionUID = 1L;

    private static final String[] columnNames = { "TABLE_CATALOG", "TABLE_SCHEMA", "TABLE_NAME", "NON_UNIQUE",
            "INDEX_QUALIFIER", "INDEX_NAME", "TYPE", "ORDINAL_POSITION", "COLUMN_NAME", "ASC_OR_DESC", "CARDINALITY",
            "PAGES", "FILTER_CONDITION" };

    public IndexTableModel(ResultSet rs)
    {
        super(rs, columnNames);
    }

}
