package ru.hh.school.testframework.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.sqlite.SQLiteDataSource;
import ru.hh.school.testframework.util.PropertyLoader;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Database {

    private static final Logger LOG = LoggerFactory.getLogger(Database.class);

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public Database() {
        Properties props = PropertyLoader.load();

        dataSource = new SQLiteDataSource();
        ((SQLiteDataSource)dataSource).setUrl(props.getProperty("db.url"));

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void cleanCoreTables() {
        LOG.info("Running reset.sql script");
        try {
            ScriptUtils.executeSqlScript(dataSource.getConnection(),
                    new FileSystemResource("src/main/resources/sql/reset.sql"));
        } catch (SQLException e) {
            LOG.error("Can't execute reset sql script");
        }
    }

    public boolean isRecordPresent(Tables table, Map<String, String> whereParams) {
        String query = String.format("SELECT * FROM %s WHERE %s;", table, getWhereParamsAsString(whereParams));
        LOG.info("Sending query '{}'", query);
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        LOG.info("Result = '{}'", result);
        return !result.isEmpty();
    }

    private String getWhereParamsAsString(Map<String, String> whereParams) {
        return whereParams.entrySet()
                .stream()
                .map(entry -> String.format("%s='%s'", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(" and "));
    }

}
