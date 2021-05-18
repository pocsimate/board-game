package boardgame.result;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Result.class)
/**
 * The data access object.
 */
public interface ResultDao {

    @SqlUpdate("""
        CREATE TABLE results (
            id IDENTITY PRIMARY KEY, 
            winner VARCHAR NOT NULL,
            opponent VARCHAR NOT NULL,
            date DATE NOT NULL)
""")
    void createTable();

    @SqlUpdate("INSERT INTO results (winner, opponent, date) VALUES (:winner, :opponent, :date)")
    @GetGeneratedKeys
    long insertResult(@BindBean Result result);

    @SqlQuery("SELECT * FROM results ORDER BY date")
    List<Result> getResults();

    @SqlUpdate("DELETE FROM results")
    void deleteResults();
}
