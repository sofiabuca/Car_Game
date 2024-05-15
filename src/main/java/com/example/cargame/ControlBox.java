package com.example.cargame;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
//import javafx.scene.paint.Color;

public class ControlBox extends HBox{

    private Label a; //Left operand
    private Label o; //Operator
    private Label b; //Right operand
    private TextField c; //Your entered result
    private Label e;  //equal sign
    double width;
    double height;
    int iResComp ;



    //Create constructor
    public ControlBox() {
        this.a = new Label();
        this.o = new Label();
        this.b = new Label();
        this.c = new TextField();
        this.e = new Label();
        this.width = width;
        this.height = height;

        addLabelsHBox();
        generateRandom();
    }

    /**
     * Add the labels to the HBox
     */
    public void addLabelsHBox(){
        //Left Number
        a.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(a);

        //Operator
        o.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(o);

        //Right Number
        b.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(b);

        //Equal sign
        e = new Label("=");
        e.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(e);

        //Text Field
        c = new TextField();
        c.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(c);
    }


    /**
     * Generate two random numbers and sOperation
     */
    public void generateRandom(){
        //Variables
        int iRanNum1;
        int iRanNum2;
        int iResult;

        //Operation random
        String sOperation = generateRandomOperation();
        o.setText(sOperation);

        c.clear();

        //Condition if is multiplication
        if(sOperation.equals("x")){
            iRanNum1 = generateRanNumMultiplication();
            a.setText(String.valueOf(iRanNum1));

            iRanNum2 = generateRanNumMultiplication();
            b.setText(String.valueOf(iRanNum2));

            //Check the answer
            iResComp = checkOperation(Integer.parseInt(a.getText()), Integer.parseInt(b.getText()), o.getText());

        }
        else {
            iRanNum1 = generateRanNum();
            a.setText(String.valueOf(iRanNum1));

            iRanNum2 = generateRanNum();
            b.setText(String.valueOf(iRanNum2));

            //Check the answer
            iResComp = checkOperation(Integer.parseInt(a.getText()), Integer.parseInt(b.getText()), o.getText());
        }
    }

    /**
     * Generate a random operation
     * @return
     */
    public String generateRandomOperation(){
        String [] operationArr = {"x", "+","-"};
        String sOperation ="";
        for(int i = 0; i < operationArr.length; i++){
            int iRandom = (int) (Math.random() * operationArr.length);
            sOperation = operationArr[iRandom];
        }
        return sOperation;
    }

    /**
     * random number btw 1 and 99
     * @return
     */
    public int generateRanNum(){
        return (int) (Math.random()*99)+1;
    }

    /**
     * random number btw 1 and 12
     * @return
     */
    public int generateRanNumMultiplication(){
        return (int) (Math.random()*12)+1;
    }

    /**
     * Method for check the operation
     * @param iNum1
     * @param iNum2
     * @param sOperation
     * @return
     */
    public int checkOperation(int iNum1, int iNum2, String sOperation){
        int iResult = 0;
        if(sOperation.equals("x")){
            iResult = iNum1 * iNum2;
        } else if (sOperation.equals("+")) {
            iResult = iNum1 + iNum2;
        }
        else if (sOperation.equals("-")) {
            iResult = iNum1 - iNum2;
        }
        return iResult;
    }

    /**
     * Check the answer
     * @return
     */
    public boolean checkAnswer(){
        return iResComp == Integer.parseInt(getC());
    }

    public String getC() {
        return c.getText();
    }

}