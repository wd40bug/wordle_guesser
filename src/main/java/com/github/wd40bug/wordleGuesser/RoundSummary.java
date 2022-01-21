package com.github.wd40bug.wordleGuesser;

import java.util.ArrayList;

public class RoundSummary {
    String answer;
    int answerValue;
    boolean victory;
    ArrayList<String> guesses;
    ArrayList<Integer> values;

    public RoundSummary() {
        guesses = new ArrayList<>();
        values = new ArrayList<>();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(int answerValue) {
        this.answerValue = answerValue;
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public ArrayList<String> getGuesses() {
        return guesses;
    }

    public void setGuesses(ArrayList<String> guesses) {
        this.guesses = guesses;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public void addGuess(String guess){
        guesses.add(guess);
    }

    public void addValue(int value){
        values.add(value);
    }

    public RoundSummary(String answer, int answerValue, boolean victory, ArrayList<String> guesses, ArrayList<Integer> values) {
        this.answer = answer;
        this.answerValue = answerValue;
        this.victory = victory;
        this.guesses = guesses;
        this.values = values;

    }
    @Override
    public String toString() {
        return answer+"| "+answerValue+"| "+victory+"| "+ String.join(", ",guesses) +"| "+ values.toString()
                .replaceAll("\\[","").replaceAll("]","")+"| "+guesses.size();
    }
}
