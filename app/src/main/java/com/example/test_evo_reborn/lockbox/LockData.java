package com.example.test_evo_reborn.lockbox;

/**
 * Created by test-evo-REBORN on 3/6/2018.
 */

public class LockData {


    private String lockName;
    private String lockCombo;

    public LockData(){

    }


    public String getLockCombo() {
        return lockCombo;
    }

    public void setLockCombo(String lockCombo) {
        this.lockCombo = lockCombo;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

}
