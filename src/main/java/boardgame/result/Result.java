package boardgame.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
/**
 * The class represents information of what gets stored in the database.
 */
public class Result {

    Long id;
    private String winner;
    private String opponent;
    private Date date;

}
