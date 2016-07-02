package sandkev.knight;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kevin on 23/06/2016.
 */
public class Controller {

    private static int MAX_VOWELS = 2;

    Board board;
    Navigator navigator;
    private final Executor pool;

    public Controller() {
        navigator = new Navigator();
        pool = Executors.newFixedThreadPool(8);
    }

    private void reset(Position initialPosition) {
        board = new Board(initialPosition);
    }

    public void play(int length){

        Position initialPosition = new Position(0, 0);

        Set<char[]> unique = new HashSet<>();

        while (true) {
            reset(initialPosition);
            char[] sequence = new char[length];
            for (int n = 0; n < length; n++) {
                //List<Move> possibleMoves = Knight.generateMoves(board); //board.possibleMoves(initialPosition);
                List<Move> possibleMoves = board.possibleMoves(initialPosition);

                //pickNextMove(n, possibleMoves, navigator)
                Move nextMove = navigator.pickNextMove(n, possibleMoves);
                sequence[n] = board.move(nextMove).getValue();
            }

            if(unique.add(sequence)){
                System.out.println(unique.size() + Arrays.toString(sequence));
            }
        }


    }
    

    public int calculate(int length) throws InterruptedException {

        Position initialPosition = new Position(0, 0);
        reset(initialPosition);

        final Set<char[]> unique = new HashSet<>();
        final AtomicInteger counter = new AtomicInteger();
        SequenceListener collector = sequence -> {
            if(unique.add(sequence)){
                counter.getAndIncrement();
                System.out.println(unique.size() + Arrays.toString(sequence));
            }
        };

        generatePath(collector, initialPosition, 0, length, 0, new char[length]);

        CountDownLatch latch = new CountDownLatch(1);
        latch.await(5, TimeUnit.MINUTES);

        return counter.get();

    }

    interface SequenceListener{
        void onSequence(char[] sequence);
    }

    private void generatePath(SequenceListener collector, Position position, int level, int length, int vowels, char[] tail) {
        //System.out.println("genPath level=" + level + " " + Arrays.toString(tail));
        char value = board.at(position).getValue();
        if(isVowel(value)){
            if(vowels<MAX_VOWELS){
                vowels++;
                tail[level]=value;
            }
        }else {
            tail[level]=value;;
        }
        if(level==length-1){
            //pool.execute(() -> collector.onSequence(tail));
            collector.onSequence(tail);

        }else {
            List<Move> possibleMoves = board.possibleMoves(position);
            for (Move move : possibleMoves) {
                final int numberOfVowels = vowels;
                pool.execute(() -> generatePath(collector, position.applyMove(move),level+1,length, numberOfVowels, tail));
            }
        }
    }

    private boolean isVowel(char c) {
        return c=='A'||c=='E'||c=='I'||c=='O'||c=='U';
    }


    public static void main(String[] args) throws InterruptedException {
        Controller controller = new Controller();
        controller.calculate(10);
        System.exit(0);
    }

}
