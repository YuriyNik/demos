package interview.functions;



public class SortingFunctions {
    public static void main(String[] args) {
        String[] data = {"YYN","YYY","YYY","YYN","YYN"};
        System.out.println(maxStreak(data,3));
        String[] data2 = {"YN","YY","YN","YY","YY"};
        System.out.println(maxStreak(data2,2));
    }

    public void fetchItemsToDisplay(){

    }

    public static int maxStreak(String[] data, int m  ){
        String target = "Y".repeat(m);
        int count = 0;
        int maxcount= 0;
        for (String value : data) {
            if (target.equals(value)) {
                count++;
                maxcount = Math.max(maxcount,count);
            }
            else count=0;
        }
        return maxcount;
    }


}
