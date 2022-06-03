package com.johnestebanap.juegodepreguntassofka.db;


//Clase o Molde Para las Preguntas,
// con esta clase se podra crear objetos con la estructura que nesesitamos
// para guardar las preguntas.
public class HistoryUser {

    private String nameUser;
    private int score;

    //constructor sin parámetros en caso tal de que se necesite al momento de trabajar con la bd
    public HistoryUser() { }

    public HistoryUser(String nameUser, int score) {
        this.nameUser = nameUser;
        this.score = score;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
