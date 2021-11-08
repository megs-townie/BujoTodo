package com.example.save_v04.placeholder;

public class CarModel {


    private long id;
    private String str_col;
    private Integer num_col;

    public CarModel() {
        this.setId(0);
        this.setModelName("default modelName");
        this.setModelNumber(0);
    }

    public CarModel(long id, String modelName, Integer modelNumber) {
        this.setId(id);
        this.setModelName(modelName);
        this.setModelNumber(modelNumber);
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + getId() +
                ", modelName='" + getModelName() + '\'' +
                ", modelNumber=" + getModelNumber();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelName() {
        return str_col;
    }

    public void setModelName(String modelName) {
        this.str_col = modelName;
    }

    public Integer getModelNumber() {
        return num_col;
    }

    public void setModelNumber(Integer modelNumber) {
        this.num_col = modelNumber;
    }


}
