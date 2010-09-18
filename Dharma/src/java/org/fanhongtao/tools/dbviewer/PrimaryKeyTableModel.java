package org.fanhongtao.tools.dbviewer;

import java.sql.ResultSet;

/**
 * @author Dharma
 * @created 2010-7-4
 */
class PrimaryKeyTableModel extends AbstractIndexTableModel
{
    private static final long serialVersionUID = 1L;

    private static final String[] columnNames = { "TABLE_CAT", "TABLE_SCHEM", "TABLE_NAME", "COLUMN_NAME", "KEY_SEQ",
            "PK_NAME" };

    public PrimaryKeyTableModel(ResultSet rs)
    {
        super(rs, columnNames);
    }
}
