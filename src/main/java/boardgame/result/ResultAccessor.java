package boardgame.result;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;
import java.util.List;

/**
 * The class that communicates directly with the database.
 */
public class ResultAccessor {
    private static Jdbi jdbi;

    public ResultAccessor() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
        try {
            createTable();
        } catch (UnableToExecuteStatementException e){
            Logger.warn("Table exists...");
        }
    }

    /**
     * Pushes the result of the game in the database
     *
     * @param result the result of the game
     */
    public void insertResult(Result result){
        try( Handle handle = jdbi.open()) {
            ResultDao dao = handle.attach(ResultDao.class);
            dao.insertResult(result);
        }
    }

    /**
     * Creates the table.
     */
    public void createTable(){
        try( Handle handle = jdbi.open()) {
            ResultDao dao = handle.attach(ResultDao.class);
            dao.createTable();
        }
    }

    /**
     * {@return the information stored in the database}
     */
    public List<Result> getResults(){
        try( Handle handle = jdbi.open()) {
            ResultDao dao = handle.attach(ResultDao.class);
            return dao.getResults();
        }
    }

}
