package org.turings.turings.mistaken;

public class StatisticsResult {
    private int bigQuestion;//大题数量
    private int choiceQuestion;//选择题数量
    private int fillBlankQuestion;//填空题数量

    @Override
    public String toString() {
        return "StatisticsResult{" +
                "bigQuestion=" + bigQuestion +
                ", choiceQuestion=" + choiceQuestion +
                ", fillBlankQuestion=" + fillBlankQuestion +
                '}';
    }

    public int getBigQuestion() {
        return bigQuestion;
    }

    public void setBigQuestion(int bigQuestion) {
        this.bigQuestion = bigQuestion;
    }

    public int getChoiceQuestion() {
        return choiceQuestion;
    }

    public void setChoiceQuestion(int choiceQuestion) {
        this.choiceQuestion = choiceQuestion;
    }

    public int getFillBlankQuestion() {
        return fillBlankQuestion;
    }

    public void setFillBlankQuestion(int fillBlankQuestion) {
        this.fillBlankQuestion = fillBlankQuestion;
    }
}
