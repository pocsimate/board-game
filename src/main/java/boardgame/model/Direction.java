package boardgame.model;

public enum Direction {

    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1),
    DOWN_RIGHT(1, 1),
    DOWN(1, 0),
    DOWN_LEFT(1, -1);

    private int rowChange;
    private int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange() {
        return rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    public static Direction of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        Direction direction = Direction.of(-1,0);
        Direction direction1 = Direction.UP_LEFT;
        System.out.println(direction1.equals(direction));
    }
}