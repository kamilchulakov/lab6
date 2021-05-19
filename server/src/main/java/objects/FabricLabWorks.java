package objects;

import logic.InputData;

import java.util.HashMap;

public class FabricLabWorks {
    public HashMap<String, LabWork> getTestingMaterial() {
        HashMap<String, LabWork> hashMap = new HashMap<>();
        hashMap.put("1", new LabWork("proga", new Coordinates(1.0f, 2), 2L,
                Difficulty.EASY, new Discipline("java", 10L)));
        hashMap.put("2", new LabWork("proga", new Coordinates(1.0f, 2), 10L,
                Difficulty.IMPOSSIBLE, new Discipline("java", 10L)));
        return hashMap;
    }
    public LabWork makeLabworkFromInputData(InputData inputData) {
        String name = inputData.getDisciplineName();
        Long hours = inputData.getSelfStudyHours();
        Discipline discipline = new Discipline(name, hours);
        Coordinates coordinates = new Coordinates(inputData.getCoordinateX(), inputData.getCoordinateY());
        LabWork labwork = new LabWork(inputData.getLabName(), coordinates, inputData.getMinimalPoint(),
                inputData.getDifficulty(),discipline);
        return labwork;
    }
}
