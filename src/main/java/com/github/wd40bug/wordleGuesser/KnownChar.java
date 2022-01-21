package com.github.wd40bug.wordleGuesser;

public record KnownChar(char character, int place) {

    public char getCharacter() {
        return character;
    }

    public int getPlace() {
        return place;
    }

}
