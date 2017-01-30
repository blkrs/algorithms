package krzych.optimization;

/**
 * Created by krzych on 30.01.17.
 */
public interface Optimization {
    default Double optimizeAlpha(final Double alpha, final Double cost) {
        return alpha;
    }
}
