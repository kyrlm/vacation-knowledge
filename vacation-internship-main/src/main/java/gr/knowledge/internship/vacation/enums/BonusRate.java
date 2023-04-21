package gr.knowledge.internship.vacation.enums;

public enum BonusRate {

    winter("Winter",1.3),
    autumn("Autumn",0.4),
    spring("Spring",0.6),
    summer("Summer",0.7);

    public final String season;
    public  final double rate;

    BonusRate(String season, double rate) {
        this.season = season;
        this.rate = rate;
    }

    public java.lang.String getSeason() {
        return season;
    }

    public double getRate() {
        return rate;
    }
}