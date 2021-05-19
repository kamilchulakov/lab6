package objects;

public enum Difficulty {
    EASY,
    IMPOSSIBLE,
    TERRIBLE;

    public static Difficulty get(String variable) throws NoDifficultyFoundException {
        String var1 = variable.toLowerCase();
        if (var1.equals("easy") | variable.equals("easy")) return EASY;
        else if (var1.equals("impossible") | variable.equals("impossible")) return IMPOSSIBLE;
        else if (var1.equals("terrible") | variable.equals("terrible")) return TERRIBLE;
        else throw new NoDifficultyFoundException();
    }
}
