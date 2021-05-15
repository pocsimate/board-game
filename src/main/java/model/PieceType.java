package model;

public enum PieceType {
    BLUE,
    RED,
    BLOCK;

    public String getPieceType() {
        return switch (this) {
            case RED -> "red";
            case BLUE -> "blue";
            case BLOCK -> "block";
        };
    }

    public static void main(String[] args) {
        PieceType pt = PieceType.BLUE;
        PieceType pt2 = PieceType.RED;

        System.out.println(pt.getPieceType());
        System.out.println(pt2.getPieceType());
    }
}
