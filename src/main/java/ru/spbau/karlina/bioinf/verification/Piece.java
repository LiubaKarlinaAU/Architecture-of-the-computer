package ru.spbau.karlina.bioinf.verification;

public class Piece implements Comparable<Piece>{
    public enum Type {B, Y}

    private Type type;
    private int index;
    private Double mass;

    public Piece(Type type, int index, double mass) {
        this.type = type;
        this.index = index;
        this.mass = new Double(mass);
    }

    /**
     * Getter function
     *
     * @return piece mass
     */
    public Double getMass() {
        return mass;
    }

    @Override
    public int compareTo(Piece o) {
        return Double.compare(mass, o.getMass());
    }

    public String getType(){
        return "" + type + index;
    }
}
