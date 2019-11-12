package com.example.words;

public class Word {
    private String word;
    private String meaning;
    private String example;
    public Word() {

    }
    public Word(String word, String meaning, String example) {
        this.word = word;
        this.meaning = meaning;
        this.example = example;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public String getMeaning() {
        return meaning;
    }
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    public String getExample() {
        return example;
    }
    public void setExample(String example) {
        this.example = example;
    }
}
