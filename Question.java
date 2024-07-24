public abstract class Question {

    private String description;
    private String answer;

    public  Question(){

    }

    public abstract void addLabels(String op);

    public abstract boolean checkAns(String str);

    public String getAnswer() {
        return answer;
    }

    public String getDescription() {
        return description;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}




