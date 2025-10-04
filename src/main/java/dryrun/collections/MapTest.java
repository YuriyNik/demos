package dryrun.collections;

import java.util.HashSet;
import java.util.Set;

public class MapTest {

    record Mister (Integer id, String name){
        @Override
        public int hashCode(){
            return id.hashCode();
        }

        @Override
        public boolean equals(Object other){
            if (this == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            Mister mister = (Mister) other;
            return this.id.equals(mister.id);
        }
    };

    public static void main(String[] args) {
        Set<Mister> misterSet = new HashSet<>();

        misterSet.add(new Mister(1, "Smith"));
        misterSet.add(new Mister(1, "John"));
        misterSet.add(new Mister(2, "Ivan"));
        System.out.println(misterSet.size());
        System.out.println(misterSet);

    }

}
