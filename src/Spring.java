public class Spring {
    private double k;

    public Spring() {
        this.k = 1.0;
    }

    public Spring(double k) {
        this.k = k;
    }

    public double getStiffness() {
        return this.k;
    }

    private void setStiffness(double k) {
        this.k = k;
    }

    public double[] move(double t, double dt, double x0, double v0) {
        int steps = (int) (t / dt);
        double[] coords = new double[steps];
        double omega = Math.sqrt(this.k);
        double x = x0;
        double v = v0;

        for (int i = 0; i < steps; i++) {
            double t_i = i * dt;
            double sin_omega_t_i = Math.sin(omega * t_i);
            double cos_omega_t_i = Math.cos(omega * t_i);
            double x_i = x * cos_omega_t_i + (v / omega) * sin_omega_t_i;
            coords[i] = x_i;
        }

        return coords;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        double[] coords = new double[(int) ((t1 - t0) / dt)];
        int i = 0;
        double t = t0;

        while (t < t1) {
            double[] stepCoords = move(dt, dt, x0, v0);
            x0 = stepCoords[stepCoords.length - 1];
            v0 = Math.sqrt(this.k) * (stepCoords[stepCoords.length - 1] - stepCoords[stepCoords.length - 2]) / dt;
            for (int j = 0; j < stepCoords.length; j++) {
                coords[i] = stepCoords[j];
                i++;
            }
            t += dt;
        }

        return coords;
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        double[] coords = move(t0, t1, dt, x0 / m, v0 / m);
        for (int i = 0; i < coords.length; i++) {
            coords[i] *= m;
        }
        return coords;
    }

    public Spring inSeries(Spring that) {
        return new Spring(this.k + that.k);
    }

    public Spring inParallel(Spring that) {
        return new Spring(1.0 / (1.0 / this.k + 1.0 / that.k));
    }
}
