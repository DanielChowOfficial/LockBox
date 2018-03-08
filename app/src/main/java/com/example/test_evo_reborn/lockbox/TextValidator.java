package com.example.test_evo_reborn.lockbox;

/**
 * Created by test-evo-REBORN on 3/2/2018.
 */

public class TextValidator {
    private static TextValidator instance;
    private TextValidator(){

    }

    public static TextValidator getInstance(){
        if(instance == null){
            instance = new TextValidator();
        }
        return instance;
    }

    public boolean isValidInput(String name, String combo, String lockName, String lockCombo){

        if(name.length() > 0
                && combo.length() > 0
                && !name.equals(lockName)
                && !combo.equals(lockCombo)){
            return true;
        }
        return false;
    }

}
