package javademo.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetIndexInfo
{
    public static void main(String[] args) throws Exception
    {
        Connection conn = getH2Connection();
        System.out.println("Got Connection.");
        Statement st = conn.createStatement();
        st.executeUpdate("drop table survey;");
        st.executeUpdate("create table survey (id int, name varchar(30), mobile varchar(20), email varchar(60), PRIMARY KEY (ID));");
        st.executeUpdate("create unique index ix_survey1 on survey (name asc, mobile asc);");
        st.executeUpdate("create index ix_survey2 on survey (email asc);");
        st.executeUpdate("insert into survey (id,name ) values (1,'nameValue')");

        ResultSet indexInformation = null;
        DatabaseMetaData meta = conn.getMetaData();

        // The '_' character represents any single character.
        // The '%' character represents any sequence of zero
        // or more characters.
        indexInformation = meta.getIndexInfo(null, null, "SURVEY", true, false);
        while (indexInformation.next())
        {
            String dbCatalog = indexInformation.getString("TABLE_CATALOG");
            String dbSchema = indexInformation.getString("TABLE_SCHEMA");
            String dbTableName = indexInformation.getString("TABLE_NAME");
            boolean dbNoneUnique = indexInformation.getBoolean("NON_UNIQUE");
            String dbIndexQualifier = indexInformation.getString("INDEX_QUALIFIER");
            String dbIndexName = indexInformation.getString("INDEX_NAME");
            short dbType = indexInformation.getShort("TYPE");
            short dbOrdinalPosition = indexInformation.getShort("ORDINAL_POSITION");
            String dbColumnName = indexInformation.getString("COLUMN_NAME");
            String dbAscOrDesc = indexInformation.getString("ASC_OR_DESC");
            int dbCardinality = indexInformation.getInt("CARDINALITY");
            int dbPages = indexInformation.getInt("PAGES");
            String dbFilterCondition = indexInformation.getString("FILTER_CONDITION");

            System.out.println("index name=" + dbIndexName);
            System.out.println("table=" + dbTableName);
            System.out.println("column=" + dbColumnName);
            System.out.println("catalog=" + dbCatalog);
            System.out.println("schema=" + dbSchema);
            System.out.println("nonUnique=" + dbNoneUnique);
            System.out.println("indexQualifier=" + dbIndexQualifier);
            System.out.println("type=" + dbType);
            System.out.println("ordinalPosition=" + dbOrdinalPosition);
            System.out.println("ascendingOrDescending=" + dbAscOrDesc);
            System.out.println("cardinality=" + dbCardinality);
            System.out.println("pages=" + dbPages);
            System.out.println("filterCondition=" + dbFilterCondition);
        }

        st.close();
        conn.close();
    }

    private static Connection getH2Connection() throws Exception
    {
        Class.forName("org.h2.Driver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:h2:tcp://localhost/dharma";
        return DriverManager.getConnection(url, "sa", "");
    }

    private static Connection getHSQLConnection() throws Exception
    {
        Class.forName("org.hsqldb.jdbcDriver");
        System.out.println("Driver Loaded.");
        String url = "jdbc:hsqldb:data/tutorial";
        return DriverManager.getConnection(url, "sa", "");
    }

    public static Connection getMySqlConnection() throws Exception
    {
        String driver = "org.gjt.mm.mysql.Driver";
        String url = "jdbc:mysql://localhost/demo2s";
        String username = "oost";
        String password = "oost";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static Connection getOracleConnection() throws Exception
    {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:caspian";
        String username = "mp";
        String password = "mp2";

        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }
}
