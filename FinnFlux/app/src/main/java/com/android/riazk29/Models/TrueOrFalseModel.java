package com.android.riazk29.Models;
/**
 * Represents a true or false question model.
 */
public class TrueOrFalseModel {
    private boolean answer; // The answer to the question (true or false)
    private String question; // The question text
    private String content; // Additional content related to the question

    /**
     * Constructs a new TrueOrFalseModel.
     */
    public TrueOrFalseModel() {
    }

    /**
     * Gets the answer to the question.
     *
     * @return The answer to the question (true or false).
     */
    public boolean isAnswer() {
        return answer;
    }

    /**
     * Sets the answer to the question.
     *
     * @param answer The answer to set.
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    /**
     * Sets the question text.
     *
     * @param question The question text to set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Sets the additional content related to the question.
     *
     * @param content The additional content to set.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the question text.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the additional content related to the question.
     *
     * @return The additional content related to the question.
     */
    public String getContent() {
        return content;
    }
}
