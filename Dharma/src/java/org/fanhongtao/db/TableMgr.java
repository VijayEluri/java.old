package org.fanhongtao.db;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.fanhongtao.log.LogUtils;
import org.fanhongtao.xml.DigesterUtils;


/**
 * @author Dharma
 * @created 2009-6-3
 */
public class TableMgr
{
    private Map<String, Table> tableMap = new HashMap<String, Table>();

    public void addTable(Table table)
    {
        tableMap.put(table.getName(), table);
    }

    public void adjust()
    {
        for (Iterator<Map.Entry<String, Table>> iter = tableMap.entrySet().iterator(); iter.hasNext();)
        {
            Map.Entry<String, Table> entry = iter.next();
            Table table = entry.getValue();
            table.adjust();
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer(1024);
        for (Iterator<Map.Entry<String, Table>> iter = tableMap.entrySet().iterator(); iter.hasNext();)
        {
            Map.Entry<String, Table> entry = iter.next();
            Table table = entry.getValue();
            buf.append(table.toString());
            buf.append("\r\n");
        }
        return buf.toString();
    }

    public static void main(String[] args)
    {
        LogUtils.initBasicLog();

        TableMgr tableMgr = new TableMgr();
        URL inputUrl = tableMgr.getClass().getResource("tables.xml");
        URL ruleUrl = tableMgr.getClass().getResource("tables_rule.xml");
        tableMgr = (TableMgr) DigesterUtils.parse(tableMgr, inputUrl, ruleUrl);
        tableMgr.adjust();
        System.out.println(tableMgr.toString());
    }
}
