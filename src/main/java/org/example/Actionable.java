package org.example;

import java.io.IOException;

public interface Actionable {
    void run() throws IOException;
    void displayMenu(); 
}
