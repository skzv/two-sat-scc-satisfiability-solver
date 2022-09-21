package dev.skz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        solve("test-data.txt");
        solve("2sat1.txt");
        solve("2sat2.txt");
        solve("2sat3.txt");
        solve("2sat4.txt");
        solve("2sat5.txt");
        solve("2sat6.txt");
    }

    private static void solve(String filename) {
        TwoSatSatisfiabilitySolver twoSatSatisfiabilitySolver = readTwoSatConstraintsFromFile(filename);
        System.out.printf("Constraints for %s are satisfiable: %s%n", filename, twoSatSatisfiabilitySolver.isSatisfiable());
    }

    private static int[] getIntTokens(String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }

    private static TwoSatSatisfiabilitySolver readTwoSatConstraintsFromFile(String filename) {
        File file = Paths.get("data/" + filename).toFile();

        TwoSatSatisfiabilitySolver solver = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            int size = Integer.parseInt(br.readLine());
            solver = new TwoSatSatisfiabilitySolver(size);

            while ((line = br.readLine()) != null) {
                int[] tokens = getIntTokens(line);
                int a = tokens[0];
                int b = tokens[1];
                // System.out.printf("%d, %d %n", a, b);
                solver.addClause(a, b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return solver;
    }
}