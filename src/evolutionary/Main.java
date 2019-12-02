package evolutionary;

import java.io.IOException;

import static evolutionary.Test.*;

public class Main {

    public static void main(String[] args) {
        try {
            performTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
