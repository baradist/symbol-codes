package cf.baradist;

import cf.baradist.algorithms.Codable;
import cf.baradist.algorithms.ShannonFano;

public class Main {
    public static void main(String[] args) {
        String s = "No kidding, Lorenzo called off his trip to visit Mexico City just because they told him the conquistadores were extinct";
        System.out.println(s);

        Codable codable = new ShannonFano();
        codable.getCodes(s).forEach(System.out::println);
        System.out.println("Average lenght of symbols is " + codable.getAverageLenght());
    }
}
