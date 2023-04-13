import java.util.Arrays;

public class FT {

    private double[] real;   // Real part of input
    private double[] imag;   // Imaginary part of input
    private double[] freq;   // Frequencies of the harmonics
    private double[] amplitude;  // Amplitude of each harmonic

    public FT(double[] x) {
        int n = x.length;
        real = new double[n];
        imag = new double[n];
        freq = new double[n];
        amplitude = new double[n/2];

        // Compute the Fourier Transform
        for (int k = 0; k < n; k++) {
            double re = 0;
            double im = 0;
            for (int j = 0; j < n; j++) {
                double angle = 2 * Math.PI * j * k / n;
                re += x[j] * Math.cos(angle);
                im -= x[j] * Math.sin(angle);
            }
            real[k] = re;
            imag[k] = im;
            freq[k] = k * 1.0 / n;
        }

        // Compute the amplitude of each harmonic
        for (int i = 0; i < n/2; i++) {
            amplitude[i] = Math.sqrt(real[i]*real[i] + imag[i]*imag[i]);
        }
    }

    public double[] getAmplitude() {
        return amplitude;
    }

    public double[] getFrequency() {
        return freq;
    }

    public static void main(String[] args) {
        double[] x = {1, 2, 3, 4, 5, 4, 3, 2};
        FT ft = new FT(x);
        System.out.println(Arrays.toString(ft.getAmplitude()));
        System.out.println(Arrays.toString(ft.getFrequency()));
    }
}

