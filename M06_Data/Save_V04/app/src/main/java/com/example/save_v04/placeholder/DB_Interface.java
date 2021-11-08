package com.example.save_v04.placeholder;

import java.util.List;
import java.util.Map;

public interface DB_Interface {

    public int count();

    public int save(CarModel carModel);

    public int update(CarModel carModel);  // Not implemented

    public int deleteById(Long id);  // Not implemented

    public List<PlaceholderContent.PlaceholderItem> findAll();

    public Map<String, PlaceholderContent.PlaceholderItem> findAllDetails();

    public String getNameById(Long id);  // Not implemented

}
