import java.io.*;
import java.util.ArrayList;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 * <p>
 * Note: This is like a tree, but some nodes in the structure have two parents.
 * <p>
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 * <p>
 * a
 * b   c
 * d   e   f
 * h   i   j   k
 * <p>
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 * <p>
 * <p>
 * See NumberTriangleTest.java for a few basic test cases.
 * <p>
 * Extra: If you decide to solve the Project Euler problems (see main),
 * feel free to add extra methods to this class. Just make sure that your
 * code still compiles and runs so that we can run the tests on your code.
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     * <p>
     * Hint: think recursively and use the idea of partial tracing from first year :)
     * <p>
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        // for fun [not for credit]:
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     * <p>
     * You can decide if you want to use a recursive or an iterative approach in your solution.
     * <p>
     * You can assume that:
     * the length of path is less than the height of this NumberTriangle structure.
     * each character in the string is either 'l' or 'r'
     *
     * @param path the path to follow through this NumberTriangle
     * @return the root value at the location indicated by path
     */
    public int retrieve(String path) {
        NumberTriangle cur = this;

        for (int i = 0; i < path.length(); i++) {
            char direction = path.charAt(i);
            if (direction == 'l') {
                cur = cur.left;
            } else if (direction == 'r') {
                cur = cur.right;
            }
        }
        if (cur == null) {
            return -1;
        }

        return cur.getRoot();

    }

    /**
     * Read in the NumberTriangle structure from a file.
     * <p>
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     * <p>
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        NumberTriangle top = null;
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = br.readLine();
        ArrayList<String> lines = new ArrayList<>();
        // will need to return the top of the NumberTriangle,
        // so might want a variable for that.
        while (line != null) {
            lines.add(line.strip());// put each line in the file as one item into arraylist
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
        int height = lines.size();
        int[][] numbers = new int[height][]; // the size of the row of numbers is the number of items in arraylist
        // form a grid and put the numbers into a grid as the form of the triangle
        for (int i = 0; i < height; i++) {
            String[] numStrings = lines.get(i).split("\\s+");
            numbers[i] = new int[numStrings.length]; // the column of number is the size of each item in arraylist
            for (int j = 0; j < numStrings.length; j++) {
                numbers[i][j] = Integer.parseInt(numStrings[j]);
            }
        }
        // form a numberTriangle 2D list and put each number in the same position in grid in this 2D list
        NumberTriangle[][] triangles = new NumberTriangle[height][];
        for (int i = 0; i < height; i++) {
            triangles[i] = new NumberTriangle[numbers[i].length];
            for (int j = 0; j < numbers[i].length; j++) {
                triangles[i][j] = new NumberTriangle(numbers[i][j]);
            }
        }
        // set the left and right relationship of the triangle
        for (int i = 0; i < height - 1; i++) {
            for (int j = 0; j < triangles[i].length; j++) {
                triangles[i][j].setLeft(triangles[i + 1][j]);
                triangles[i][j].setRight(triangles[i + 1][j + 1]);
            }
        }
        top = triangles[0][0];
        return top;
    }

    public static void main(String[] args) throws IOException {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        mt.maxSumPath();
        System.out.println(mt.getRoot());
    }
}
