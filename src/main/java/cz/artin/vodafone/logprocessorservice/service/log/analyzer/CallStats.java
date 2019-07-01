package cz.artin.vodafone.logprocessorservice.service.log.analyzer;

public class CallStats {

    private int numberOfCalls;
    private double averageDuration;

    public CallStats(int numberOfCalls, double averageDuration) {
        this.numberOfCalls = numberOfCalls;
        this.averageDuration = averageDuration;
    }

    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void setNumberOfCalls(int numberOfCalls) {
        this.numberOfCalls = numberOfCalls;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }
}
