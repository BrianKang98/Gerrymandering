public class isContiguous {
    public static void main(String[] args) {
        // input result in matrix format
        // x_i_j = district i, precincts j
        // ==1 iff republican
        int[][] mat = new int[4][23];
        int[] d1 = {2,7,17,19,20,22};   // result
        int[] d2 = {1,3,5,10,16,18};
        int[] d3 = {9,12,13,14,23};
        int[] d4 = {4,6,8,11,15,21};

        for (int i = 1; i <= 4; i++) {
            int[] arr;
            if (i == 1) {
                arr = d1;
            } else if (i==2) {
                arr = d2;
            } else if (i == 3) {
                arr = d3;
            } else {
                arr = d4;
            }
            int val;
            for (int j = 1; j <= arr.length; j++) {
                val = arr[j-1];
                mat[i-1][val-1] = val;
            }
        }
//        for (int i = 0; i < mat.length; i++) {
//            for (int j = 0; j < mat[i].length; j++) {
//                System.out.print(mat[i][j] + " ");
//            }
//            System.out.println();
//        }

        // check if district (row) is contiguous

        // can't do it....
    }
}
