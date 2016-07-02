package sandkev.knight;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kevin on 23/06/2016.
 */
public class Controller {

    private static int MAX_VOWELS = 2;

    Board board;
    Navigator navigator;
    private final Executor pool;

    public Controller() {
        this.board = new Board();
        navigator = new Navigator();
        pool = Executors.newFixedThreadPool(8);
    }

    private static class Data{
        int numberOfVowels;
        StringBuilder sb;
        public Data() {
            this(new StringBuilder(), 0);
        }
        private Data(StringBuilder sb, int numberOfVowels){
            this.sb = sb;
            this.numberOfVowels = numberOfVowels;
        }
        public boolean add(char value){
            if(isVowel(value)){
                numberOfVowels++;
            }
            if(numberOfVowels < 3){
                sb.append(value);
                return true;
            }else {
                return false;
            }
        }
        private boolean isVowel(char c) {
            return c=='A'||c=='E'||c=='I'||c=='O'||c=='U';
        }
        public char tail(){
            return sb.charAt(sb.length()-1);
        }
        public static Data of(Data data){
            return new Data(data.sb, data.numberOfVowels);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;

            if (numberOfVowels != data.numberOfVowels) return false;
            return !(sb != null ? !sb.equals(data.sb) : data.sb != null);

        }

        @Override
        public int hashCode() {
            int result = numberOfVowels;
            result = 31 * result + (sb != null ? sb.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return sb.toString();
        }
    }


    public void play(int length){

        Position initialPosition = new Position(0, 0);

        Set<Data> sequences = new HashSet<>();
        Data data = new Data();
        data.add(board.at(initialPosition).getValue());
        sequences.add(data);
        for (int n = 0; n < length; n++) {
            sequences.addAll(buildSequences(sequences));
        }

        for (Data sequence : sequences) {
            System.out.println(sequence);
        }
        System.out.println("total = " + sequences.size());

    }

    private Set<Data> buildSequences(Set<Data> dataSets) {
        Set<Data> paths = new HashSet<>();
        for (Data data : dataSets) {
            char value = data.tail();
            char[] chars = board.charsFor(value);
            for (char c : chars) {
                Data next = Data.of(data);
                if(next.add(c)){
                    paths.add(next);
                }

            }
        }
        return paths;
    }

    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        controller.play(10);
        System.exit(0);
    }

}
