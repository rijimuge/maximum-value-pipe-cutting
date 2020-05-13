import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Homework7 {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];
        int[][] preInput = processInput(input);

        int n = preInput[0][0];
        int end = preInput[0][1];

        int[] s = new int[n];
        int[] f = new int[n];
        int[] val = new int[n];
        int[] id = new int[n];
        List<Integer> sfList = new ArrayList<>();

        for (int i = 0; i < preInput.length - 1; i++) {
            id[i] = preInput[i + 1][0];
            s[i] = preInput[i + 1][1];
            f[i] = preInput[i + 1][2];
            val[i] = preInput[i + 1][3];
            sfList.add(s[i]);
            sfList.add(f[i]);
        }
        Integer[] sfArray = new Integer[sfList.size()];
        sfList.toArray(sfArray);
        Arrays.sort(sfArray);

        List<List<Integer>> a = new LinkedList<>();
        for (int i = 0; i < end + 1; i++) {
            a.add(new LinkedList<>());
        }

        int[] maxValue = new int[end + 1];


        boolean multiple;
        boolean initial = true;
        int justCalculated = 0;
        int last = 0;
        List<Integer> lastList = new LinkedList<>();

        for (int i = 0; i < sfArray.length; i++) {
            if (i != 0 && sfArray[i] == sfArray[i - 1]) {

                continue;
            }
            if (initial) {
                initial = false;
                maxValue[sfArray[i]] = 0;
            } else {
                maxValue[sfArray[i]] = justCalculated;
                a.set(sfArray[i], lastList);
                for (int j = 0; j < n; j++) {
                    if (f[j] <= sfArray[i]) {
                        if (maxValue[sfArray[i]] == maxValue[s[j]] + val[j]) {
                            List<Integer> temp = new LinkedList<>(a.get(s[j]));
                            if (!temp.equals(a.get(sfArray[i]))) {
                                temp.add(id[j]);
                                if (!temp.equals(a.get(sfArray[i]))) {
                                }
                            }
                        }
                        if (maxValue[sfArray[i]] < maxValue[s[j]] + val[j]) {
                            a.get(s[j]).add(id[j]);
                            lastList = a.get(s[j]);
                            maxValue[sfArray[i]] = maxValue[s[j]] + val[j];
                        }
                    }
                }
            }
            justCalculated = maxValue[sfArray[i]];
            last = sfArray[i];
        }
        List<Integer> pass1 = new ArrayList<>(a.get(last));
        int max1 = justCalculated;

        //Run through from the end to see if there's an alternate maximum solution

        a = new LinkedList<>();
        for (int i = 0; i < end + 1; i++) {
            a.add(new LinkedList<>());
        }
        maxValue = new int[end + 1];
        initial = false;
        justCalculated = 0;
        last = 0;
        int set = 0;
        boolean flag = false;

        for (int i = sfArray.length - 1; i >=0 ; i--) {
            if (i != 0 && sfArray[i] == sfArray[i - 1]) {

                continue;
            }
            if (initial) {
                initial = false;
                maxValue[sfArray[i]] = 0;
            } else {
                maxValue[sfArray[i]] = justCalculated;
                a.set(sfArray[i], a.get(f[set]));
                for (int j = n - 1; j >= 0; j--) {
                    if (s[j] >= sfArray[i]) {

                        if (maxValue[sfArray[i]] < maxValue[f[j]] + val[j]) {

                            flag = true;
                            set = j;

                            maxValue[sfArray[i]] = maxValue[f[j]] + val[j];
                        }
                    }
                }
                if (flag) {
                    flag = false;
                    a.get(f[set]).add(set);

                    last = f[set];
                }
            }
            justCalculated = maxValue[sfArray[i]];
        }
        List<Integer> toRemove = new LinkedList<>();
        int lastPiece = a.get(f[set]).get(a.get(f[set]).size() - 1);
        for (int aId : a.get(last).subList(0, a.get(last).size() - 1)) {


            if (s[aId] < f[lastPiece] && f[aId] > s[lastPiece]) {
                    toRemove.add(aId);
                }
        }
        a.get(last).removeAll(toRemove);

        List<Integer> pass2 = new ArrayList<>(a.get(last));
        for (int i = 0; i < pass2.size(); i++) {
            pass2.set(i, id[pass2.get(i)]);
        }

        Collections.sort(pass2);
        int max2 = justCalculated;

        //Compare pass1 and pass2 to see if the solutions are identical
        if (max1 == max2 && pass1.equals(pass2)) {
            multiple = false;
        } else {
            multiple = true;
        }

        try {
            FileWriter fileWriter = new FileWriter(output);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(max1);
            printWriter.println(pass1);
            if (multiple) {
                printWriter.println("IT HAS MULTIPLE SOLUTIONS");
            } else {
                printWriter.println("IT HAS A UNIQUE SOLUTION");
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("IO exception encountered");
        }
    }


    private static int[][] processInput(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        int[][] processedLines = new int[lines.size()][4];

        //set n and I
        processedLines[0][0] = Integer.parseInt(lines.get(0).split("\\s+")[0]);
        processedLines[0][1] = Integer.parseInt(lines.get(0).split("\\s+")[1]);

        // Sort input by finish time
        lines.subList(1, lines.size()).sort(
            (String s1, String s2) ->
                Integer.parseInt(s2.split("\\s+")[2]) -
                    Integer.parseInt(s1.split("\\s+")[2]));
        //convert to 2D array of integers
        for (int i = 1; i < lines.size(); i++) {
            for (int j = 0; j < 4; j++) {
                processedLines[i][j] = Integer.parseInt(lines.get(i).split("\\s+")[j]);
            }
        }
        return processedLines;
    }
    private static void greedyActivitySelector(List<List<Integer>> activities) {

    }
}
