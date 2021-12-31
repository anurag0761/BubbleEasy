package com.app.bubbleeasy.model;

import java.io.Serializable;
import java.util.List;

public class Item {

    public boolean selected;
    public int row;
    public int column;

    public Item() {

    }

    public Item(int rowItem, int columnItem, boolean isSelected) {
        row = rowItem;
        column = columnItem;
        selected = isSelected;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}