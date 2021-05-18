package boardgame.result;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.List;

@RegisterBeanMapper(Result.class)
public interface ResultDao {

    @SqlUpdate("""
        CREATE TABLE results(
            id IDENTITY PRIMARY KEY, 
            start DATE NOT NULL,
            winner VARCHAR NOT NULL,
            opponent VARCHAR NOT NULL,
        )
    """)
    void createTable();

    @SqlUpdate("INSERT INTO results (start, winner, opponent) VALUES (:start, :winner, :opponent)")
    @GetGeneratedKeys
    long insertResult(@Bind("start") LocalDate start, @Bind("winner") String winner, @Bind("opponent") String opponent);

    @SqlQuery("SELECT * FROM results ORDER BY start")
    List<Result> getResults();
}
