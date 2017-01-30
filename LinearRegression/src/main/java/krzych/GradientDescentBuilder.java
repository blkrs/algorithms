package krzych;

import krzych.learningstrategy.LearningStrategy;
import krzych.learningstrategy.LinearRegressionStrategy;
import krzych.optimization.NoOptimization;
import krzych.optimization.Optimization;

/**
 * Created by krzych on 24.01.17.
 */
public class GradientDescentBuilder {
    private Double alpha =  0.1;
    private Double lambda = 0.0;
    private Double gradientThreshold = 0.00000001;
    private LearningStrategy learningStrategy = new LinearRegressionStrategy();
    private Optimization optimization = new NoOptimization();

    public GradientDescentBuilder setAlpha(Double alpha) {
        this.alpha = alpha;
        return this;
    }

    public GradientDescentBuilder setLambda(Double lambda) {
        this.lambda = lambda;
        return this;
    }

    public GradientDescentBuilder setThreshold(Double threshold) {
        this.gradientThreshold = threshold;
        return this;
    }

    public GradientDescentBuilder setLearningStrategy(LearningStrategy strategy){
        this.learningStrategy = strategy;
        return this;
    }

    public GradientDescentBuilder setOptimization(Optimization opt){
        this.optimization = opt;
        return this;
    }

    public GradientDescent build() {
        return new GradientDescent(alpha,
                                  lambda,
                                  gradientThreshold,
                                  learningStrategy,
                                  optimization
                );
    }
}
