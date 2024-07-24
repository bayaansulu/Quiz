import java.util.*;

public class Test extends Question{

    private String[] options ;
    private int numOfOptions = 4;
    private ArrayList<String> labels = new ArrayList<String>();

    public void setOptions(String[] options){
        this.options = options;
    }
    public String getOptionAt(int i){
        String ans = options[i];
        return ans;
    }
    public int getIndexOfLabel(String label){
        return this.labels.indexOf(label);
    }
    public String toString(int i){
        Collections.shuffle(Arrays.asList(this.options));
        String ans = this.options[i];
//                + "\n" +
//                this.labels.get(0) + ") " + this.getOptionAt(0) + "\n" +
//                this.labels.get(1) + ") " + this.getOptionAt(1) + "\n" +
//                this.labels.get(2) + ") " + this.getOptionAt(2) + "\n" +
//                this.labels.get(3) + ") " + this.getOptionAt(3);
        return ans;
    }
    public String toString1(){
        return this.options[0];
    }
    public String toString2(){
        return this.options[1];
    }
    public String toString3(){
        return this.options[2];
    }
    public String toString4(){
        return this.options[3];
    }
    Test(){
        this.labels.add("A");
        this.labels.add("B");
        this.labels.add("C");
        this.labels.add("D");
    }
    @Override
    public void addLabels(String op) {
    }
    @Override
    public boolean checkAns(String str) {
        return false;
    }
}

