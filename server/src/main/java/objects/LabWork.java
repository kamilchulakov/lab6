package objects;

import java.time.LocalDate;

/**
 * LabWork class
 */
public class LabWork implements Comparable<LabWork>{
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long minimalPoint; //Поле не может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле не может быть null
    private Discipline discipline; //Поле может быть null
    private String author;

    public LabWork(String name2, Coordinates coordinates2, Long minimalPoint2,
                   Difficulty difficulty2, Discipline discipline2) {
        name = name2;
        coordinates = coordinates2;
        minimalPoint = minimalPoint2;
        difficulty = difficulty2;
        discipline = discipline2;
        if (name2 != null) id = (int) (name2.length() + minimalPoint);
        else id = Math.toIntExact(minimalPoint);
        creationDate = LocalDate.now();
        author = "default";
    }
    public LabWork(String name2, Coordinates coordinates2, Long minimalPoint2,
                   Difficulty difficulty2, Discipline discipline2, String time, String author) {
        name = name2;
        coordinates = coordinates2;
        minimalPoint = minimalPoint2;
        difficulty = difficulty2;
        discipline = discipline2;
        if (name2 != null) id = (int) (name2.length() + minimalPoint);
        else id = Math.toIntExact(minimalPoint);
        creationDate = LocalDate.parse(time);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getMinimalPoint() {
        return minimalPoint;
    }

    public void setMinimalPoint(Long minimalPoint) {
        this.minimalPoint = minimalPoint;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public void copyFromLabwork(LabWork labWork1) {
        setName(labWork1.getName());
        setCoordinates(labWork1.getCoordinates());
        setDifficulty(labWork1.getDifficulty());
        setDiscipline(labWork1.getDiscipline());
        setMinimalPoint(labWork1.getMinimalPoint());
    }

    @Override
    public int compareTo(LabWork o) {
        return getId() - o.getId();
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + getStringForCreationDate() +
                ", minimalPoint=" + minimalPoint +
                ", difficulty=" + difficulty +
                ", discipline=" + discipline +
                ", author=" + author + '}';
    }
    private String getStringForCreationDate() {
        return "{" +
                "year=" + creationDate.getYear() +
                ", month=" + creationDate.getMonth() +
                ", monthValue=" + creationDate.getMonthValue() +
                ", dayOfMonth=" + creationDate.getDayOfMonth() +
                ", chronology={id=" + creationDate.getChronology().getId() +
                ", calendarType=" + creationDate.getChronology().getCalendarType() +
                "}, dayOfWeek=" + creationDate.getDayOfWeek() +
                ", leapYear=" + creationDate.isLeapYear() +
                ", dayOfYear=" + creationDate.getDayOfYear() +
                ", era=" + creationDate.getEra() +
                "}";
    }
}
