package com.example.sistemadeanlisisdegrafos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableDynamic {
    private TableLayout tableLayout;
    private Context context;
    private String[]header;
    private ArrayList<String[]>data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexC;
    private int indexR;
    private boolean multicolor=false;
    Drawable firtColor, secondColor;

    public TableDynamic(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    public void addHeadler(String[] header){
        this.header=header;
        createHeader();
    }

    public void addData(ArrayList<String[]> data){
        this.data = data;
        createDataTable();
    }

    private void newRow(){
        tableRow = new TableRow(context);
    }

    private void newCell(){
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(25);
    }

    private void createHeader(){
        indexC=0;
        newRow();
        while(indexC<header.length){
            newCell();
            txtCell.setText(header[indexC++]);
            txtCell.setTextSize(16);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow);
    }

    private void createDataTable(){
        String info;
        for(indexR=0; indexR<data.size(); indexR++){
            newRow();
            for (indexC=0; indexC<header.length; indexC++){
                newCell();
                String[] row = data.get(indexR);
                info=(indexC<row.length)?row[indexC] :"";
                txtCell.setText(info);
                tableRow.addView(txtCell,newTableRowParams());
            }
            tableLayout.addView(tableRow);
        }
    }

    public void addItems(String[]item){
        String info;
        data.add(item);
        indexC=0;
        newRow();
        while(indexC<header.length){
            newCell();
            info=(indexC<item.length)?item[indexC++] :"";
            txtCell.setText(info);
            txtCell.setTextSize(16);
            tableRow.addView(txtCell,newTableRowParams());
        }
        tableLayout.addView(tableRow,data.size());
        reColoring();
    }

    public void backgroundHeader(Drawable fondo){
        indexC=0;
        while(indexC<header.length){
            txtCell = getCell(0,indexC++);
            txtCell.setBackground(fondo);
        }
    }

    public void backgroundData(Drawable firtColor, Drawable secondColor){
       for(indexR=1; indexR<data.size(); indexR++){
            multicolor=true;
            for (indexC=0; indexC<header.length; indexC++){
                txtCell=getCell(indexR,indexC);
                txtCell.setBackground((multicolor)?firtColor:secondColor);
            }
            multicolor=false;
        }
        this.firtColor=firtColor;
        this.secondColor=secondColor;
    }

    public void reColoring(){
        indexC=0;
        multicolor=true;
        while(indexC<header.length){
            txtCell = getCell(data.size(),indexC++);
            txtCell.setBackground((multicolor)?firtColor:secondColor);
            multicolor=false;
        }
    }

    private TableRow getRow(int index){
        return (TableRow) tableLayout.getChildAt(index);
    }

    public TextView getCell(int rowIndex, int columIndex){
        tableRow = getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columIndex);
    }

    private TableRow.LayoutParams newTableRowParams(){
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1,1,1,1);
        params.weight=1;
        return params;
    }
}
