public class PeakDistance {
    
    private Peak peak1;
    private Peak peak2;
    private double distance;

    public PeakDistance(Peak peak1, Peak peak2, double distance) {
        this.peak1 = peak1;
        this.peak2 = peak2;
        this.distance = distance;
    }

    public Peak getPeak1() {
        return peak1;
    }

    public Peak getPeak2() {
        return peak2;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "The two peaks are located at (" + peak1 + ") and (" + peak2 + ")";
    }
}
